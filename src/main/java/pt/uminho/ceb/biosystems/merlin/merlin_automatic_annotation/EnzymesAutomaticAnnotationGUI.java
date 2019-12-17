package pt.uminho.ceb.biosystems.merlin.merlin_automatic_annotation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import org.apache.commons.lang.ArrayUtils;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.ParamSpec;
import es.uvigo.ei.aibench.core.operation.OperationDefinition;
import es.uvigo.ei.aibench.workbench.InputGUI;
import es.uvigo.ei.aibench.workbench.ParamsReceiver;
import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.aibench.workbench.utilities.Utilities;
import pt.uminho.ceb.biosystems.merlin.gui.datatypes.annotation.AnnotationEnzymesAIB;
import pt.uminho.ceb.biosystems.merlin.gui.utilities.AIBenchUtils;
import pt.uminho.ceb.biosystems.merlin.services.annotation.AnnotationEnzymesServices;

public class EnzymesAutomaticAnnotationGUI extends javax.swing.JDialog implements InputGUI{

	private static final long serialVersionUID = 1L;
	private AnnotationEnzymesAIB homologyDataContainer;
	private javax.swing.JButton jButtonApply;
	private javax.swing.JButton jButtonCancel;
	private javax.swing.JCheckBox jCheckBox1;
	private javax.swing.JCheckBox jCheckDefaultAnnotation;
	private javax.swing.JCheckBox jCheckBox2;
	private javax.swing.JCheckBox jCheckBox3;
	private javax.swing.JCheckBox jCheckBox4;
	private javax.swing.JCheckBox jCheckBox5;
	private javax.swing.JCheckBox jCheckBox6;
	private javax.swing.JCheckBox jCheckBox7;
	private javax.swing.JCheckBox jCheckBox8;
	private javax.swing.JCheckBox jCheckBox9;
	private javax.swing.JComboBox<String> jComboBox1;
	private javax.swing.JComboBox<String> jComboBox10;
	private javax.swing.JComboBox<String> jComboBox11;
	private javax.swing.JComboBox<String> jComboBox12;
	private javax.swing.JComboBox<String> jComboBox13;
	private javax.swing.JComboBox<String> jComboBox14;
	private javax.swing.JComboBox<String> jComboBox15;
	private javax.swing.JComboBox<String> jComboBox16;
	private javax.swing.JComboBox<String> jComboBox17;
	private javax.swing.JComboBox<String> jComboBox18;
	private javax.swing.JComboBox<String> jComboBox2;
	private javax.swing.JComboBox<String> jComboBox3;
	private javax.swing.JComboBox<String> jComboBox4;
	private javax.swing.JComboBox<String> jComboBox5;
	private javax.swing.JComboBox<String> jComboBox6;
	private javax.swing.JComboBox<String> jComboBox7;
	private javax.swing.JComboBox<String> jComboBox8;
	private javax.swing.JComboBox<String> jComboBox9;
	private javax.swing.JComboBox<String> workspace;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	private javax.swing.JTextField jTextField5;
	private javax.swing.JTextField jTextField6;
	private javax.swing.JTextField jTextField7;
	private javax.swing.JTextField jTextField8;
	private javax.swing.JTextField jTextField9;

	private String[] items;
	private String[] organisms;
	private String[] genus;
	private String organism = "";
	private String organismGenus;
	private String blastDatabase;
	private String eValueBlast;

	private int indexOrganism = -1;
	private int indexGenus = 0;

	private String inputColumn1; //ComboBox species/genus
	private String inputColumn2; //ComboBox list of species/genus available
	private double inputColumn3; //TextField e-value
	private boolean inputColumn4; //CheckBox reviewed
	private boolean inputAcceptDefault;

	private List<String> listInputColumn1 = new ArrayList<>(); //ComboBox species/genus
	private List<String> listInputColumn2 = new ArrayList<>(); //ComboBox list of species/genus available
	private List<Double> listInputColumn3 = new ArrayList<>(); //TextField e-value
	private List<Boolean> listInputColumn4 = new ArrayList<>(); //CheckBox reviewed

	private String[] workspaces;
	private String workspaceName;

	public EnzymesAutomaticAnnotationGUI() {

		super(Workbench.getInstance().getMainFrame());

		List<String> projects = AIBenchUtils.getProjectNames();

		workspaces = new String[projects.size()];
		workspace = new JComboBox<>(projects.toArray(workspaces));
		
		this.homologyDataContainer = (AnnotationEnzymesAIB) AIBenchUtils.getEntity(workspace.getSelectedItem().toString(), AnnotationEnzymesAIB.class);
		homologyDataContainer.getWorkspace().getTaxonomyID();
		this.organism = homologyDataContainer.getWorkspace().getOrganismName();
		this.workspaceName = homologyDataContainer.getWorkspace().getName();
		
		getAllOrganisms();

		initGUI();

		initialVariables();

		this.setVisible(true);		
		//		this.setAlwaysOnTop(true);
		this.toFront();
		this.setTitle("automatic workflow");

		Utilities.centerOnOwner(this);
		pack();
		setLocationRelativeTo(null);


	}


	private void initGUI() {

		items = new String[] { "", "species", "genus" };

		java.awt.GridBagConstraints gridBagConstraints;

		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox<>();
		jComboBox2 = new javax.swing.JComboBox<>();
		jLabel1 = new javax.swing.JLabel();
		jTextField1 = new javax.swing.JTextField();
		jCheckBox1 = new javax.swing.JCheckBox();
		jComboBox3 = new javax.swing.JComboBox<>();
		jComboBox4 = new javax.swing.JComboBox<>();
		jTextField2 = new javax.swing.JTextField();
		jTextField3 = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jCheckBox2 = new javax.swing.JCheckBox();
		jLabel5 = new javax.swing.JLabel();
		jComboBox5 = new javax.swing.JComboBox<>();
		jComboBox6 = new javax.swing.JComboBox<>();
		jLabel6 = new javax.swing.JLabel();
		jCheckBox3 = new javax.swing.JCheckBox();
		jLabel7 = new javax.swing.JLabel();
		jComboBox7 = new javax.swing.JComboBox<>();
		jComboBox8 = new javax.swing.JComboBox<>();
		jLabel8 = new javax.swing.JLabel();
		jTextField4 = new javax.swing.JTextField();
		jCheckBox4 = new javax.swing.JCheckBox();
		jLabel9 = new javax.swing.JLabel();
		jComboBox9 = new javax.swing.JComboBox<>();
		jComboBox10 = new javax.swing.JComboBox<>();
		jLabel10 = new javax.swing.JLabel();
		jTextField5 = new javax.swing.JTextField();
		jCheckBox5 = new javax.swing.JCheckBox();
		jLabel11 = new javax.swing.JLabel();
		jComboBox11 = new javax.swing.JComboBox<>();
		jComboBox12 = new javax.swing.JComboBox<>();
		jLabel12 = new javax.swing.JLabel();
		jTextField6 = new javax.swing.JTextField();
		jCheckBox6 = new javax.swing.JCheckBox();
		jLabel13 = new javax.swing.JLabel();
		jComboBox13 = new javax.swing.JComboBox<>();
		jComboBox14 = new javax.swing.JComboBox<>();
		jLabel14 = new javax.swing.JLabel();
		jTextField7 = new javax.swing.JTextField();
		jCheckBox7 = new javax.swing.JCheckBox();
		jLabel15 = new javax.swing.JLabel();
		jComboBox15 = new javax.swing.JComboBox<>();
		jComboBox16 = new javax.swing.JComboBox<>();
		jLabel16 = new javax.swing.JLabel();
		jTextField8 = new javax.swing.JTextField();
		jLabel17 = new javax.swing.JLabel();
		jComboBox17 = new javax.swing.JComboBox<>();
		jComboBox18 = new javax.swing.JComboBox<>();
		jLabel18 = new javax.swing.JLabel();
		jTextField9 = new javax.swing.JTextField();
		jCheckBox8 = new javax.swing.JCheckBox();
		jCheckBox9 = new javax.swing.JCheckBox();
		jButtonApply = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jCheckDefaultAnnotation = new javax.swing.JCheckBox();
		jLabel19 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridBagLayout());


		jLabel3.setText("1.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(23, 25, 0, 0);
		getContentPane().add(jLabel3, gridBagConstraints);

		jLabel4.setText("2.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel4, gridBagConstraints);

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(20, 29, 0, 0);
		getContentPane().add(jComboBox1, gridBagConstraints);


		jComboBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox2ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(20, 12, 0, 0);
		getContentPane().add(jComboBox2, gridBagConstraints);

		jLabel1.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(23, 33, 0, 0);
		getContentPane().add(jLabel1, gridBagConstraints);

		jTextField1.setText(this.eValueBlast);
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 44;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(20, 5, 0, 0);
		getContentPane().add(jTextField1, gridBagConstraints);

		jCheckBox1.setText("Reviewed");
		jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 18, 0, 35);
		getContentPane().add(jCheckBox1, gridBagConstraints);

		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox3ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox3, gridBagConstraints);

		//        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox4ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox4, gridBagConstraints);

		jTextField2.setText(this.eValueBlast);
		jTextField2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField2ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 44;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField2, gridBagConstraints);

		jTextField3.setText(this.eValueBlast);
		jTextField3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField3ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField3, gridBagConstraints);

		jLabel2.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel2, gridBagConstraints);

		jCheckBox2.setText("Reviewed");
		jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox2ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox2, gridBagConstraints);

		jLabel5.setText("3.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel5, gridBagConstraints);

		jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox5ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox5, gridBagConstraints);

		//        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox6ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox6, gridBagConstraints);

		jLabel6.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel6, gridBagConstraints);

		jCheckBox3.setText("Reviewed");
		jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox3ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox3, gridBagConstraints);

		jLabel7.setText("4.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel7, gridBagConstraints);

		jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox7ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox7, gridBagConstraints);

		//        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox8ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox8, gridBagConstraints);

		jLabel8.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel8, gridBagConstraints);

		jTextField4.setText(this.eValueBlast);
		jTextField4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField4ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField4, gridBagConstraints);

		jCheckBox4.setText("Reviewed");
		jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox4ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox4, gridBagConstraints);

		jLabel9.setText("5.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel9, gridBagConstraints);

		jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox9ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox9, gridBagConstraints);

		//        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox10.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox10ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox10, gridBagConstraints);

		jLabel10.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel10, gridBagConstraints);

		jTextField5.setText(this.eValueBlast);
		jTextField5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField5ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField5, gridBagConstraints);

		jCheckBox5.setText("Reviewed");
		jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox5ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox5, gridBagConstraints);

		jLabel11.setText("6.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel11, gridBagConstraints);

		jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox11ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox11, gridBagConstraints);

		//        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox12.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox12ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox12, gridBagConstraints);

		jLabel12.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel12, gridBagConstraints);

		jTextField6.setText(this.eValueBlast);
		jTextField6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField6ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField6, gridBagConstraints);

		jCheckBox6.setText("Reviewed");
		jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox6ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox6, gridBagConstraints);

		jLabel13.setText("7.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel13, gridBagConstraints);

		jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox13.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox13ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox13, gridBagConstraints);

		//        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox14.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox14ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox14, gridBagConstraints);

		jLabel14.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel14, gridBagConstraints);

		jTextField7.setText(this.eValueBlast);
		jTextField7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField7ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField7, gridBagConstraints);

		jCheckBox7.setText("Reviewed");
		jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox7ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox7, gridBagConstraints);

		jLabel15.setText("8.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel15, gridBagConstraints);

		jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox15.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox15ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox15, gridBagConstraints);

		//        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox16ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox16, gridBagConstraints);

		jLabel16.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel16, gridBagConstraints);

		jTextField8.setText(this.eValueBlast);
		jTextField8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField8ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField8, gridBagConstraints);

		jLabel17.setText("9.");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 25, 0, 0);
		getContentPane().add(jLabel17, gridBagConstraints);

		jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox17.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox17ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 29, 0, 0);
		getContentPane().add(jComboBox17, gridBagConstraints);

		//        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(items));
		jComboBox18.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox18ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 336;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 12, 0, 0);
		getContentPane().add(jComboBox18, gridBagConstraints);

		jLabel18.setText("e-value (xEy format)");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel18, gridBagConstraints);

		jTextField9.setText(this.eValueBlast);
		jTextField9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField9ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 45;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(19, 5, 0, 0);
		getContentPane().add(jTextField9, gridBagConstraints);

		jCheckBox8.setText("Reviewed");
		jCheckBox8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox8ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox8, gridBagConstraints);

		jCheckBox9.setText("Reviewed");
		jCheckBox9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox9ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 24;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 35);
		getContentPane().add(jCheckBox9, gridBagConstraints);

		jButtonApply.setText("Apply");
		jButtonApply.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 27;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 20;
		gridBagConstraints.ipady = 8;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(55, 133, 24, 0);
		getContentPane().add(jButtonApply, gridBagConstraints);

		jButtonCancel.setText("Cancel");
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);

			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 27;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 12;
		gridBagConstraints.ipady = 8;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(55, 18, 24, 35);
		getContentPane().add(jButtonCancel, gridBagConstraints);

		jCheckDefaultAnnotation.setText("Accept default annotation if no match is found");
		jCheckDefaultAnnotation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox10ActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 27;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.ipady = 15;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(18, 29, 0, 0);
		getContentPane().add(jCheckDefaultAnnotation, gridBagConstraints);


		//Workspace JComboBox
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 27;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.ipadx = 36;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(20, 29, 0, 0);
		
		this.workspace.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				homologyDataContainer = (AnnotationEnzymesAIB) AIBenchUtils.getEntity(workspace.getSelectedItem().toString(), AnnotationEnzymesAIB.class);
				homologyDataContainer.getWorkspace().getTaxonomyID();
				organism = homologyDataContainer.getWorkspace().getOrganismName();
				workspaceName = homologyDataContainer.getWorkspace().getName();
				
				getAllOrganisms();

				initGUI();

				initialVariables();

				setVisible(true);		
				//		this.setAlwaysOnTop(true);
				toFront();
				setTitle("automatic workflow");

//				Utilities.centerOnOwner(this);
//				pack();
//				setLocationRelativeTo(null);
				
			}
		});
		
		getContentPane().add(workspace, gridBagConstraints);


		jLabel19.setText("workspace");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 27;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		gridBagConstraints.insets = new java.awt.Insets(22, 33, 0, 0);
		getContentPane().add(jLabel19, gridBagConstraints);


		jComboBox2.setPrototypeDisplayValue("text here");
		jComboBox4.setPrototypeDisplayValue("text here");
		jComboBox6.setPrototypeDisplayValue("text here");
		jComboBox8.setPrototypeDisplayValue("text here");
		jComboBox10.setPrototypeDisplayValue("text here");
		jComboBox12.setPrototypeDisplayValue("text here");
		jComboBox14.setPrototypeDisplayValue("text here");
		jComboBox16.setPrototypeDisplayValue("text here");
		jComboBox18.setPrototypeDisplayValue("text here");


	}

	//pipeline option 1
	private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           

		if(jComboBox1.getSelectedIndex() == 1) {

			jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

		}
		else if(jComboBox1.getSelectedIndex() == 2) {

			jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

		}
		else {
			jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>());

		}
	}  

	private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}

	//pipeline option 2
	private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {                                           

		if(jComboBox3.getSelectedIndex() == 1) {

			jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

		}
		else if(jComboBox3.getSelectedIndex() == 2) {

			jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

		}
		else {
			jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>());

		}
	}

	private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}     

	private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}

	//pipeline option 3
	private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {                                           

		if(jComboBox5.getSelectedIndex() == 1) {

			jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox6.setEnabled(true);
			jLabel6.setEnabled(true);
			jCheckBox3.setEnabled(true);
			jTextField3.setEnabled(true);

		}
		else if(jComboBox5.getSelectedIndex() == 2) {

			jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox6.setEnabled(true);
			jLabel6.setEnabled(true);
			jCheckBox3.setEnabled(true);
			jTextField3.setEnabled(true);

		}
		else {
			jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox6.setEnabled(false);
			jLabel6.setEnabled(false);
			jCheckBox3.setEnabled(false);
			jTextField3.setEnabled(false);
		}
	}                                          

	private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}

	//pipeline option 4
	private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {                                           

		if(jComboBox7.getSelectedIndex() == 1) {

			jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox8.setEnabled(true);
			jLabel8.setEnabled(true);
			jCheckBox4.setEnabled(true);
			jTextField4.setEnabled(true);

		}
		else if(jComboBox7.getSelectedIndex() == 2) {

			jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox8.setEnabled(true);
			jLabel8.setEnabled(true);
			jCheckBox4.setEnabled(true);
			jTextField4.setEnabled(true);

		}
		else {
			jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox8.setEnabled(false);
			jLabel8.setEnabled(false);
			jCheckBox4.setEnabled(false);
			jTextField4.setEnabled(false);
		}
	}                                          

	private void jComboBox8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	//pipeline option 5
	private void jComboBox9ActionPerformed(java.awt.event.ActionEvent evt) {                                           

		if(jComboBox9.getSelectedIndex() == 1) {

			jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox10.setEnabled(true);
			jLabel10.setEnabled(true);
			jCheckBox5.setEnabled(true);
			jTextField5.setEnabled(true);

		}
		else if(jComboBox9.getSelectedIndex() == 2) {

			jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox10.setEnabled(true);
			jLabel10.setEnabled(true);
			jCheckBox5.setEnabled(true);
			jTextField5.setEnabled(true);

		}
		else {
			jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox10.setEnabled(false);
			jLabel10.setEnabled(false);
			jCheckBox5.setEnabled(false);
			jTextField5.setEnabled(false);
		}
	}                                          

	private void jComboBox10ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	//pipeline option 6
	private void jComboBox11ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		if(jComboBox11.getSelectedIndex() == 1) {

			jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox12.setEnabled(true);
			jLabel12.setEnabled(true);
			jCheckBox6.setEnabled(true);
			jTextField6.setEnabled(true);

		}
		else if(jComboBox11.getSelectedIndex() == 2) {

			jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox12.setEnabled(true);
			jLabel12.setEnabled(true);
			jCheckBox6.setEnabled(true);
			jTextField6.setEnabled(true);

		}
		else {
			jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox12.setEnabled(false);
			jLabel12.setEnabled(false);
			jCheckBox6.setEnabled(false);
			jTextField6.setEnabled(false);
		}
	}                                           

	private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox6ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}

	//pipeline option 7
	private void jComboBox13ActionPerformed(java.awt.event.ActionEvent evt) { 

		if(jComboBox13.getSelectedIndex() == 1) {

			jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox14.setEnabled(true);
			jLabel14.setEnabled(true);
			jCheckBox7.setEnabled(true);
			jTextField7.setEnabled(true);

		}
		else if(jComboBox13.getSelectedIndex() == 2) {

			jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox14.setEnabled(true);
			jLabel14.setEnabled(true);
			jCheckBox7.setEnabled(true);
			jTextField7.setEnabled(true);

		}
		else {
			jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox14.setEnabled(false);
			jLabel14.setEnabled(false);
			jCheckBox7.setEnabled(false);
			jTextField7.setEnabled(false);
		}
	}                                           

	private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}

	//pipeline option 8
	private void jComboBox15ActionPerformed(java.awt.event.ActionEvent evt) {    

		if(jComboBox15.getSelectedIndex() == 1) {

			jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox16.setEnabled(true);
			jLabel16.setEnabled(true);
			jCheckBox8.setEnabled(true);
			jTextField8.setEnabled(true);

		}
		else if(jComboBox15.getSelectedIndex() == 2) {

			jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox16.setEnabled(true);
			jLabel16.setEnabled(true);
			jCheckBox8.setEnabled(true);
			jTextField8.setEnabled(true);

		}
		else {
			jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox16.setEnabled(false);
			jLabel16.setEnabled(false);
			jCheckBox8.setEnabled(false);
			jTextField8.setEnabled(false);
		}
	}                                           

	private void jComboBox16ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}        

	private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox8ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	//pipeline option 9
	private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {                                            

		if(jComboBox17.getSelectedIndex() == 1) {

			jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(this.organisms));

			jComboBox18.setEnabled(true);
			jLabel18.setEnabled(true);
			jCheckBox9.setEnabled(true);
			jTextField9.setEnabled(true);

		}
		else if(jComboBox17.getSelectedIndex() == 2) {

			jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(this.genus));

			jComboBox18.setEnabled(true);
			jLabel18.setEnabled(true);
			jCheckBox9.setEnabled(true);
			jTextField9.setEnabled(true);

		}
		else {
			jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>());

			jComboBox18.setEnabled(false);
			jLabel18.setEnabled(false);
			jCheckBox9.setEnabled(false);
			jTextField9.setEnabled(false);
		}

	}                                           
	private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}

	private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void jCheckBox9ActionPerformed(java.awt.event.ActionEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	//Check box "accept default annotation if no match is found"
	private void jCheckBox10ActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	//Apply button
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         

		getInput();

		//    	new EvaluatorEA(blastDatabase, listInputColumn1, listInputColumn2, listInputColumn3, listInputColumn4, inputAcceptDefault, homologyDataContainer);

		System.out.println(homologyDataContainer.getWorkspace().getName());

		try {
			ParamSpec[] paramsSpec = new ParamSpec[]{
					new ParamSpec("blastDatabase", String.class, blastDatabase, null),
					new ParamSpec("listInputColumn1", List.class, listInputColumn1, null),
					new ParamSpec("listInputColumn2", List.class, listInputColumn2, null),
					new ParamSpec("listInputColumn3", List.class, listInputColumn3, null),
					new ParamSpec("listInputColumn4", List.class, listInputColumn4, null),
					new ParamSpec("inputAcceptDefault", Boolean.class, inputAcceptDefault, null),
					new ParamSpec("homologyDataContainer", AnnotationEnzymesAIB.class, homologyDataContainer, null),
			};

			for (@SuppressWarnings("rawtypes") OperationDefinition def : Core.getInstance().getOperations()){
				if (def.getID().equals("operations.EnzymesAutomaticAnnotation.ID")){

					Workbench.getInstance().executeOperation(def, paramsSpec);
				}
			}

		} catch (IllegalArgumentException e) {

			Workbench.getInstance().error("An error occurred while performing the evaluation!");

			e.printStackTrace();
		}


		simpleFinish();
	}                                        

	//Cancel Button
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         

		simpleFinish();

	}


	/**
	 * Method to close the window (to be applied in the cancel button)
	 */
	public void simpleFinish() {

		this.setVisible(false);
		this.dispose();
	}


	/**
	 * Method to retrieve the organisms to put in the second combo box of a pipeline option, when "species" is selected in the first one,
	 * and the genus to put in the second combo box of a pipeline option, when "genus" is selected in the first one
	 */
	private void getAllOrganisms() {

		try {

			this.blastDatabase = AnnotationEnzymesServices.getLastestUsedBlastDatabase(this.workspaceName);
			
			this.eValueBlast = AnnotationEnzymesServices.getBlastEValue(this.workspaceName, this.blastDatabase).toString();


			String[] result = AnnotationEnzymesServices.getAllOrganisms(this.workspaceName);
			String[] result2 = AnnotationEnzymesServices.getAllGenus(this.workspaceName);
			
			Arrays.sort(result);
			Arrays.sort(result2);

			for(int i = 0; i < result.length; i++) {

				String auxName = result[i];
				
				if(auxName.equalsIgnoreCase(this.organism)) {
					this.indexOrganism = i+1;
				}
				else if(auxName.replace("strain", "").replaceAll("[^A-Za-z0-9]", "").equalsIgnoreCase(this.organism.replaceAll("[^A-Za-z0-9]", ""))) {
					this.indexOrganism = i+1;
				}
				
			}
			
			//select the index of the organism genus in the genus list
			String[] organismsplit= organism.split(" ");
			this.organismGenus = organismsplit[0];
			
			for(int i = 0; i < result2.length; i++) {
				if(result2[i].equals(this.organismGenus)) {
					this.indexGenus = i + 1;
				}
			}

			String[] any = new String[1];
			any[0] = "any";

			this.organisms = (String[]) ArrayUtils.addAll(any, result);
			this.genus = (String[]) ArrayUtils.addAll(any, result2);

		} 
		catch (Exception e) {
			Workbench.getInstance().error(e);
			e.printStackTrace();
		}		
	}


	/**
	 * Method to initiate some variables of the GUI.
	 */
	private void initialVariables() {

		//set default of the first combo box of pipeline options 1, 2 and 3
		jComboBox1.setSelectedIndex(1);
		jComboBox3.setSelectedIndex(2);
		jComboBox5.setSelectedIndex(1);

		//set default of the second combo box of pipeline options 1 and 2

		jComboBox2.setSelectedIndex(this.indexOrganism);
		jComboBox4.setSelectedIndex(this.indexGenus);
		jComboBox6.setSelectedIndex(0);

		//set checked as default of the "reviewed" parameter of pipeline options 1 and 2
		jCheckBox1.setSelected(true);
		jCheckBox2.setSelected(true);
		jCheckBox3.setSelected(true);

		//set all the parameters (except the first combo box) of the pipeline options 3 to 9 as inactive
		//		jLabel6.setEnabled(false);
		jLabel8.setEnabled(false);
		jLabel10.setEnabled(false);
		jLabel12.setEnabled(false);
		jLabel14.setEnabled(false);
		jLabel16.setEnabled(false);
		jLabel18.setEnabled(false);

		//		jComboBox6.setEnabled(false);
		jComboBox8.setEnabled(false);
		jComboBox10.setEnabled(false);
		jComboBox12.setEnabled(false);
		jComboBox14.setEnabled(false);
		jComboBox16.setEnabled(false);
		jComboBox18.setEnabled(false);

		//		jTextField3.setEnabled(false);
		jTextField4.setEnabled(false);
		jTextField5.setEnabled(false);
		jTextField6.setEnabled(false);
		jTextField7.setEnabled(false);
		jTextField8.setEnabled(false);
		jTextField9.setEnabled(false);

		//		jCheckBox3.setEnabled(false);
		jCheckBox4.setEnabled(false);
		jCheckBox5.setEnabled(false);
		jCheckBox6.setEnabled(false);
		jCheckBox7.setEnabled(false);
		jCheckBox8.setEnabled(false);
		jCheckBox9.setEnabled(false);

		//set checked as default of the "accept default annotation if no match is found" parameter
		jCheckDefaultAnnotation.setSelected(true);	
	}

	/**
	 * store the user inputs in arraylists to be used by the evaluator
	 */
	private void getInput() {

		//pipeline options line 1
		inputColumn1 = (String)jComboBox1.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox2.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField1.getText());

			inputColumn4 = jCheckBox1.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 2
		inputColumn1 = (String)jComboBox3.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox4.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField2.getText());

			inputColumn4 = jCheckBox2.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 3
		inputColumn1 = (String)jComboBox5.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox6.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField3.getText());

			inputColumn4 = jCheckBox3.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 4
		inputColumn1 = (String)jComboBox7.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox8.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField4.getText());

			if(jCheckBox4.isSelected()) {

				inputColumn4 = jCheckBox3.isSelected();

				listInputColumn1.add(inputColumn1);
				listInputColumn2.add(inputColumn2);
				listInputColumn3.add(inputColumn3);
				listInputColumn4.add(inputColumn4);
			}
		}
		//pipeline options line 5
		inputColumn1 = (String)jComboBox9.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox10.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField5.getText());

			inputColumn4 = jCheckBox5.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 6
		inputColumn1 = (String)jComboBox11.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox12.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField6.getText());

			inputColumn4 = jCheckBox6.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 7
		inputColumn1 = (String)jComboBox13.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox14.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField7.getText());

			inputColumn4 = jCheckBox7.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 8
		inputColumn1 = (String)jComboBox15.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox16.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField8.getText());

			inputColumn4 = jCheckBox8.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		//pipeline options line 9
		inputColumn1 = (String)jComboBox17.getSelectedItem();

		if(inputColumn1 != "") {
			inputColumn2 = (String)jComboBox18.getSelectedItem();
			inputColumn3 = Double.valueOf(jTextField9.getText());

			inputColumn4 = jCheckBox9.isSelected();

			listInputColumn1.add(inputColumn1);
			listInputColumn2.add(inputColumn2);
			listInputColumn3.add(inputColumn3);
			listInputColumn4.add(inputColumn4);
		}

		
		this.inputAcceptDefault = jCheckDefaultAnnotation.isSelected();

	}

	@Override
	public void init(ParamsReceiver receiver, OperationDefinition<?> operation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValidationError(Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}
}