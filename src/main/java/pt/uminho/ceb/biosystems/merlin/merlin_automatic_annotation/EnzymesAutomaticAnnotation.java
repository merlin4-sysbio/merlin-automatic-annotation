package pt.uminho.ceb.biosystems.merlin.merlin_automatic_annotation;

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.uvigo.ei.aibench.core.operation.annotation.Cancel;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;
import pt.uminho.ceb.biosystems.merlin.aibench.datatypes.annotation.AnnotationEnzymesAIB;
import pt.uminho.ceb.biosystems.merlin.aibench.gui.CustomGUI;
import pt.uminho.ceb.biosystems.merlin.aibench.utilities.MerlinUtils;
import pt.uminho.ceb.biosystems.merlin.aibench.utilities.TimeLeftProgress;
import pt.uminho.ceb.biosystems.merlin.core.datatypes.WorkspaceDataTable;
import pt.uminho.ceb.biosystems.merlin.core.datatypes.WorkspaceGenericDataTable;
import pt.uminho.ceb.biosystems.merlin.database.connector.databaseAPI.HomologyAPI;
import pt.uminho.ceb.biosystems.merlin.database.connector.datatypes.Connection;
import pt.uminho.ceb.biosystems.merlin.utilities.io.FileUtils;

@Operation(name="enzymes automatic annotation", description="enzymes automatic annotation")
public class EnzymesAutomaticAnnotation {

	private static final String ACCEPT_DEFAULT_NOTE = "default annotation";
	private static final String REJECT_MESSAGE = "rejected";
	private static final String ERROR_MESSAGE = "an error occurred while evaluating";
	private static final String SPECIES = "species";
	private static final String GENUS = "genus";
	private String[] listConfidenceLevel = {"A", "B", "C", "D", "E", "F", "G", "H", "I"}; //confidence levels to be assigned to the result

	private List<String> inputColumn1 = new ArrayList<>(); //ComboBox species/genus
	private List<String> inputColumn2 = new ArrayList<>(); //ComboBox list of species/genus available
	private List<Double> inputColumn3 = new ArrayList<>(); //TextField e-value
	private List<Boolean> inputColumn4 = new ArrayList<>(); //CheckBox reviewed
	private Boolean inputAcceptDefault;
	private String blastDatabase;
	private AnnotationEnzymesAIB homologyDataContainer;
	private Map<Integer, String> locusTag;
	private Map<Integer, String> geneName;
	private Map<Integer, String> ecMap;
	private Map<Integer, String> confLevelMap;

	private AtomicBoolean cancel = new AtomicBoolean();
	private TimeLeftProgress progress = new TimeLeftProgress();

	private int locusTagColumn = 1;
	private int geneNameColumn = 3;

	@Port(direction=Direction.INPUT, name="blastDatabase", order=1)
	public void setEcCurated(String blastDatabase){

		this.blastDatabase = blastDatabase;
	};

	@Port(direction=Direction.INPUT, name="inputColumn1", order=2)
	public void setinputColumn1(ArrayList<String> inputColumn1){

		this.inputColumn1 = inputColumn1;
	};

	@Port(direction=Direction.INPUT, name="inputColumn2", order=3)
	public void setinputColumn2(ArrayList<String> inputColumn2){

		this.inputColumn2 = inputColumn2;
	};

	@Port(direction=Direction.INPUT, name="inputColumn3", order=4)
	public void setinputColumn3(ArrayList<Double> inputColumn3){

		this.inputColumn3 = inputColumn3;
	};

	@Port(direction=Direction.INPUT, name="inputColumn4", order=5)
	public void setinputColumn4(List<Boolean> inputColumn4){

		this.inputColumn4 = inputColumn4;
	};

	@Port(direction=Direction.INPUT, name="inputAcceptDefault", order=6)
	public void setInputAcceptDefault(Boolean inputAcceptDefault){

		this.inputAcceptDefault = inputAcceptDefault;
	};


	@Port(direction=Direction.INPUT, name="homologyDataContainer", order=7)
	public void setEnzymesAnnotationDataInterface(AnnotationEnzymesAIB homologyDataContainer){

		try {

			this.homologyDataContainer = homologyDataContainer;

			Connection connection = homologyDataContainer.getConnection();
			Statement statement = connection.createStatement();
			int continueQuestion = CustomGUI.stopQuestion("continue?", "all annotations previously saved in the database will be lost, do you wish to continue?", new String[]{"yes", "no"});

			if(continueQuestion==0) {

				HomologyAPI.deleteHomologyData(blastDatabase, statement);

				Set<Integer> hits = getAllHits(statement);

				applyPipelineOptions(hits);
			}

		}
		catch (Exception e) {

			e.printStackTrace();
			Workbench.getInstance().error("An error occurred while performing the evaluation!");
		}
	};

	public Set<Integer> getAllHits(Statement statement){

		Set<Integer> result = new HashSet<>();

		try {

			result = HomologyAPI.getSKeyForAutomaticAnnotation(blastDatabase, statement);

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void applyPipelineOptions(Set<Integer> hits) {

		Map<Integer, Integer> sKeyToRow = homologyDataContainer.getTableRowIndex();

		long startTime = GregorianCalendar.getInstance().getTimeInMillis();

		ecMap = new HashMap<>();
		confLevelMap = new HashMap<>();
		geneName = new HashMap<>();
		locusTag = new HashMap<>();

		int p = 0;

		WorkspaceGenericDataTable mainTableData = homologyDataContainer.getAllGenes(blastDatabase, true);

		for(Integer sKey : hits) {

			if(!this.cancel.get()) {

				p++;

				if(sKeyToRow.containsKey(sKey)) {

					int row = sKeyToRow.get(sKey);

					boolean resultNotFound = true;

					WorkspaceDataTable dataTable = getHomologyResults(homologyDataContainer.getKeys().get(row));

					int dataSize = dataTable.getRowCount();
					
					for(int k=0; k < inputColumn1.size() && resultNotFound; k++) { //indice a ir buscar nas listas do pipeline
					
						for(int i = 0; i < dataSize && resultNotFound; i++) { //cada linha das annotations

							String confidenceLevel = listConfidenceLevel[k];
							
							boolean reviewed = Integer.valueOf((String) dataTable.getValueAt(i, 2)) != 0;
							String organism = (String) dataTable.getValueAt(i, 3);
							double eValue = Double.valueOf((String) dataTable.getValueAt(i, 4));
							String ecNumbers = (String)dataTable.getValueAt(i, 7);
							
							String firstInput = inputColumn1.get(k);
							String secondInput = inputColumn2.get(k).trim();
							double thirdEvalue = inputColumn3.get(k);
							boolean forthReviewed = inputColumn4.get(k);
							
							boolean goEvalue = thirdEvalue >= eValue ;
							boolean goReviewed = forthReviewed == reviewed; 

							if(firstInput.equalsIgnoreCase(SPECIES)) {  

								if(secondInput.equals("any")) { //we only have to check the e-value and if the entry is reviewed; accept the first one that meets the requirements 

									if(goEvalue && goReviewed) {

										ecMap.put(sKey, getEcNumber(ecNumbers, mainTableData, row));

										confLevelMap.put(sKey, confidenceLevel);

										resultNotFound = false;
									}
								}
								else {

									if(secondInput.equalsIgnoreCase(organism) && goEvalue && goReviewed) {

										ecMap.put(sKey, getEcNumber(ecNumbers, mainTableData, row));

										confLevelMap.put(sKey, confidenceLevel);

										resultNotFound = false;
									}
								}	
							}

							if(firstInput.equalsIgnoreCase(GENUS)) {

								String[] organismSplit = (organism).split(" ");
								String getGenus = organismSplit[0].trim();

								if(secondInput.equals("any")) {

									if(goEvalue && goReviewed) {

										ecMap.put(sKey, getEcNumber(ecNumbers, mainTableData, row));

										confLevelMap.put(sKey, confidenceLevel);

										resultNotFound = false;
									}
								}

								else{ 

									if(secondInput.equals(getGenus) && goEvalue && goReviewed) {

										ecMap.put(sKey, getEcNumber(ecNumbers, mainTableData, row));

										confLevelMap.put(sKey, confidenceLevel);

										resultNotFound = false;
									}
								}	
							}
						}
					}

					if(resultNotFound == true) {
						
						if(inputAcceptDefault) {
							
							this.ecMap.put(sKey, null);
							this.confLevelMap.put(sKey, ACCEPT_DEFAULT_NOTE);
						}
						else {
							
							ecMap.put(sKey, "");
							confLevelMap.put(sKey, REJECT_MESSAGE);
						}
					}


					String name = (String) mainTableData.getValueAt(row, geneNameColumn);
					String locus = (String) mainTableData.getValueAt(row, locusTagColumn);

					if(name == null)
						name = "";

					if(locus == null)
						locus = "";

					geneName.put(sKey, name);
					locusTag.put(sKey, locus);
				} 
				else {

					if(sKey != null) {

						ecMap.put(sKey, "");
						confLevelMap.put(sKey, ERROR_MESSAGE);
					}
				}

				if(this.cancel.get())
					p = hits.size();

				progress.setTime(GregorianCalendar.getInstance().getTimeInMillis() - startTime, p, hits.size());
			}

		}

		if(!this.cancel.get())
			saveResult();
	}

	public void saveResult() {

		try {
			
			long startTime = GregorianCalendar.getInstance().getTimeInMillis();
			
			progress.setTime(0, 0, 0, "saving results");

			Connection connection = homologyDataContainer.getConnection();
			HomologyAPI.insertAutomaticEnzymeAnnotation(connection, locusTag, geneName, ecMap, confLevelMap);

			////////////
			progress.setTime(0, 0, 0, "saving report");
			
			String path = FileUtils.getWorkspaceTaxonomyFolderPath(this.homologyDataContainer.getName(), this.homologyDataContainer.getWorkspace().getTaxonomyID());

			Calendar cal = new GregorianCalendar();

			// Get the components of the time
			String hour24 = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));     // 0..23
			String min = String.valueOf(cal.get(Calendar.MINUTE));             // 0..59
			String day = String.valueOf(cal.get(Calendar.DAY_OF_YEAR));		//0..365

			int rowCounter = 0, columnCounter = 0;

			Workbook  wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(EnzymesAutomaticAnnotation.class.getSimpleName().toString());

			Row excelRow = sheet.createRow(rowCounter++);

			excelRow.createCell(columnCounter++).setCellValue("genes");
			excelRow.createCell(columnCounter++).setCellValue("annotation");
			excelRow.createCell(columnCounter++).setCellValue("score");
			excelRow.createCell(columnCounter++).setCellValue("confidence level");
			excelRow.createCell(columnCounter++).setCellValue("previous annotation status");
			excelRow.createCell(columnCounter++).setCellValue("previous annotation");
			excelRow.createCell(columnCounter++).setCellValue("score");

			for(int key : ecMap.keySet()) {

				columnCounter=0;

				Integer row = homologyDataContainer.getTableRowIndex().get(key);
				String currentAnnotation = this.homologyDataContainer.getItemsList().get(1).get(row),
						newAnnotation = this.ecMap.get(key);
				
				if(newAnnotation == null && this.confLevelMap.get(key).equals(ACCEPT_DEFAULT_NOTE))
					newAnnotation = currentAnnotation;

				excelRow = sheet.createRow(rowCounter++);
				excelRow.createCell(columnCounter++).setCellValue(this.locusTag.get(key));
				excelRow.createCell(columnCounter++).setCellValue(newAnnotation);
				if(newAnnotation != null)
					excelRow.createCell(columnCounter++).setCellValue(homologyDataContainer.getECPercentage(newAnnotation,row));
				else
					excelRow.createCell(columnCounter++).setCellValue("");

				excelRow.createCell(columnCounter++).setCellValue(this.confLevelMap.get(key));
				String status = "distinct";
				if(newAnnotation != null && newAnnotation.equalsIgnoreCase(currentAnnotation))
					status = "same";
				excelRow.createCell(columnCounter++).setCellValue(status);
				excelRow.createCell(columnCounter++).setCellValue(currentAnnotation);
				if(currentAnnotation != null)
					excelRow.createCell(columnCounter++).setCellValue(homologyDataContainer.getECPercentage(currentAnnotation,row));
				else
					excelRow.createCell(columnCounter++).setCellValue("");
				
				progress.setTime(GregorianCalendar.getInstance().getTimeInMillis() - startTime, rowCounter,  ecMap.keySet().size(), "saving report");
			}

			/////////////////////////
			rowCounter = 0;
			columnCounter = 0;

			sheet = wb.createSheet("Worflow configuration");
			excelRow = sheet.createRow(rowCounter++);
			excelRow.createCell(columnCounter++).setCellValue("taxa type");
			excelRow.createCell(columnCounter++).setCellValue("taxon");
			excelRow.createCell(columnCounter++).setCellValue("e-value");
			excelRow.createCell(columnCounter++).setCellValue("UniProt status");

			for(int k=0; k < inputColumn1.size(); k++) {

				columnCounter=0;
				excelRow = sheet.createRow(rowCounter++);
				excelRow.createCell(columnCounter++).setCellValue(inputColumn1.get(k));
				excelRow.createCell(columnCounter++).setCellValue(inputColumn2.get(k));
				excelRow.createCell(columnCounter++).setCellValue(inputColumn3.get(k));
				excelRow.createCell(columnCounter++).setCellValue(Boolean.valueOf(inputColumn4.get(k)));
			}

			excelRow = sheet.createRow(rowCounter++);
			excelRow = sheet.createRow(rowCounter++);
			columnCounter=0;
			excelRow.createCell(columnCounter++).setCellValue("Accept default annotation if no match is found");
			excelRow.createCell(columnCounter++).setCellValue(Boolean.valueOf(this.inputAcceptDefault));

			String excelFileName = path.concat(EnzymesAutomaticAnnotation.class.getSimpleName().concat("_").concat(blastDatabase).concat(hour24).concat("_").concat(min).concat("_").concat(day).concat(".xlsx"));
			FileOutputStream fileOut = new FileOutputStream(excelFileName);
			wb.write(fileOut);
			fileOut.flush();
			wb.close();
			fileOut.close();
		}
		catch (Exception e) {

			Workbench.getInstance().error("An error occurred while saving the evaluation data!");
			e.printStackTrace();
		}

		MerlinUtils.updateEnzymesAnnotationView(homologyDataContainer.getWorkspace().getName());

		Workbench.getInstance().info("The automatic enzyme annotation process has finished!");
	}
	
	/**
	 * @param row
	 * @return
	 */
	public WorkspaceDataTable getHomologyResults(int row) {

		Statement statement;

//		try {
//
//			statement = this.connection.createStatement();		//create hibernate connection here...

//			String program = HomologyAPI.getSetupProgram(statement);

			List<String> columnsNames = new ArrayList<String>();

			columnsNames.add("reference ID");
			columnsNames.add("locus ID");
			columnsNames.add("status");
			columnsNames.add("organism");
			columnsNames.add("e Value");
			columnsNames.add("score (bits)");
			columnsNames.add("product");
			columnsNames.add("EC number");

			WorkspaceDataTable res = new WorkspaceDataTable(columnsNames, "");

//			for(ArrayList<String> lists : HomologyAPI.getHomologyResults(row, statement))
//				res.addLine(lists);

			return res;

//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
	}


	/**
	 * @param ecNumber
	 * @param mainTableData
	 * @param row
	 * @return
	 */
	public static String getEcNumber(String query, WorkspaceGenericDataTable mainTableData, Integer row){

		if(query.contains(", ")){

			String[] ecNumbers = (String[]) mainTableData.getValueAt(row, 6);

			String[] splited = query.split(",\\s");

			for(String ecNumber : ecNumbers){

				String[] splited2 = ecNumber.split(", ");

				Arrays.sort(splited);
				Arrays.sort(splited2);

				if(Arrays.equals(splited, splited2))				
					return ecNumber;
			}

			return query;		
		}
		else{
			return query;
		}
	}

	/**
	 * @param mainTableData
	 * @return
	 */
	public Integer getEcNumberColumnIndex(WorkspaceGenericDataTable mainTableData){

		String[] columnNames = mainTableData.getColumnsNames();

		for(int i=0; i<columnNames.length; i++){

			if(columnNames[i].equalsIgnoreCase("EC number(s)"))
				return i;
		}
		return 6;
	}


	/**
	 * @return the progress
	 */
	@Progress
	public TimeLeftProgress getProgress() {

		return progress;
	}

	/**
	 * @param cancel the cancel to set
	 */
	@Cancel
	public void setCancel() {

		progress.setTime(0, 0, 0);
		Workbench.getInstance().warn("operation canceled!");
		this.cancel.set(true);
	}
}		

