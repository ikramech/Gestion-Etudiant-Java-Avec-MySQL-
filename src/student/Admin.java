/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.RowFilter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import java.util.Comparator;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import student.Etudiant;
import java.sql.Connection;
import javax.swing.table.TableRowSorter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.OutputStream; 

import java.io.File;


import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/**
 *
 * @author ikram
 */
public class Admin extends javax.swing.JFrame {

  public Etudiant etudiant;
     private ArrayList<Etudiant> etudiants;
     
    public Admin() {
        initComponents();
         etudiants = new ArrayList<>();
           remplir();
          
       
    }
private boolean verifierNote(float note) {
    return note >= 0 && note <= 20;
}


////////////////////////////////PDF

private void extrairePDF() {
    Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream("votre_fichier.pdf"));
        document.open();
        
        // Add a title to the PDF document
        document.add(new Paragraph("Liste des étudiants"));

        // Create a table for the PDF document
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // Add column names to the PDF document with spacing
        StringBuilder columnNames = new StringBuilder();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnNames.append(model.getColumnName(i)).append("\t\t\t"); // Add spacing after each column name
        }
        document.add(new Paragraph(columnNames.toString()));

        // Add each row of the table to the PDF document with spacing
        for (int i = 0; i < model.getRowCount(); i++) {
            StringBuilder rowData = new StringBuilder();
            for (int j = 0; j < model.getColumnCount(); j++) {
                rowData.append(model.getValueAt(i, j)).append("\t\t\t"); // Add spacing after each cell value
            }
            document.add(new Paragraph(rowData.toString()));
        }

        JOptionPane.showMessageDialog(this, "Extraction en PDF réussie !");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erreur lors de l'extraction en PDF : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    } finally {
        document.close();
    }
}
private void ajouterEtudiant() {
    boolean notesValides = false;
    
    while (!notesValides) {
        // Récupérer les données saisies par l'utilisateur           String cinPassport = jTextField1.getText();
     String cinPassport = jTextField1.getText();

        String nom = jTextField2.getText();
    String prenom = jTextField3.getText();
    int age = Integer.parseInt(jTextField4.getText());
    String adresse = jTextField5.getText();
    String phone = jTextField6.getText();
    float java = Float.parseFloat(jTextField7.getText());
    float cPlusPlus = Float.parseFloat(jTextField8.getText());
    float conception = Float.parseFloat(jTextField9.getText());
    float anglais = Float.parseFloat(jTextField10.getText());
    float francais = Float.parseFloat(jTextField11.getText());

      

        // Vérifier si les notes sont valides
       
        if (verifierNote(java) && verifierNote(cPlusPlus) && verifierNote(conception) && 
             verifierNote(francais) && verifierNote(anglais)) {
            
            // Créer un objet Etudiant avec les données récupérées
            Etudiant e = new Etudiant(cinPassport, nom, prenom, age, adresse, phone, java, cPlusPlus, conception, anglais, francais);

            // Ajouter l'étudiant à la liste des étudiants
            etudiants.add(e);

            // Ajouter l'étudiant à la base de données
     ajouterEtudiantBaseDeDonnees(e);

            // Sortir de la boucle car les notes sont valides
            notesValides = true;
        } else {
            // Afficher un message d'erreur et demander à l'utilisateur de ressaisir les notes
            JOptionPane.showMessageDialog(null, "Veuillez saisir des notes valides (entre 0 et 20).");
            // Effacer les champs de saisie pour permettre une nouvelle saisie
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
            jTextField8.setText("");
            jTextField9.setText("");
            jTextField10.setText("");
            jTextField11.setText("");
        }
    }
}

         














private void ajouterEtudiantBaseDeDonnees(Etudiant etudiant) {
        // Établir une connexion à la base de données
java.sql.Connection connection = connectionn.getConnection();
        if (connection != null) {
            try {
                // Préparer la requête d'insertion
                String query = "INSERT INTO basededonnes (id, nom, prenom, age, adress, telephone, java, c, conception,francais, anglais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

                // Remplir les paramètres de la requête avec les données de l'étudiant
            // Remplissage des paramètres de la requête avec les données de l'étudiant
            preparedStatement.setString(1, etudiant.getCinPassport());
            preparedStatement.setString(2, etudiant.getNom());
            preparedStatement.setString(3, etudiant.getPrenom());
            preparedStatement.setInt(4, etudiant.getAge());
            preparedStatement.setString(5, etudiant.getAdresse());
            preparedStatement.setString(6, etudiant.getPhone());
            preparedStatement.setFloat(7, etudiant.getJava());
            preparedStatement.setFloat(8, etudiant.getCPlusPlus());
            preparedStatement.setFloat(9, etudiant.getConception());
            preparedStatement.setFloat(10, etudiant.getAnglais());
            preparedStatement.setFloat(11, etudiant.getFrancais());

                // Exécuter la requête
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Données de l'étudiant enregistrées avec succès !");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement des données de l'étudiant : " + ex.getMessage());
            } finally {
                try {
                    // Fermer la connexion
                    connection.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la fermeture de la connexion : " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la connexion à la base de données.");
        }
    }

private void remplir() {
    DefaultTableModel model = new DefaultTableModel();
    jTable1.setModel(model);

    // Ajout des colonnes au modèle de tableau
    model.addColumn("id");
    model.addColumn("nom");
    model.addColumn("prenom");
    model.addColumn("age");
    model.addColumn("adress");
    model.addColumn("telephone");
    model.addColumn("java");
    model.addColumn("c");
    model.addColumn("conception");
    model.addColumn("francais");
    model.addColumn("anglais");
    model.addColumn("Moyenne"); // Ajoutez la colonne de moyenne ici

    try {
        java.sql.Connection connection = connectionn.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM basededonnes");

        // Parcours des résultats de la requête et ajout des données au modèle de tableau
        while (resultSet.next()) {
            String cinPassport = resultSet.getString("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String age = resultSet.getString("age");
            String adress = resultSet.getString("adress");
            String telephone = resultSet.getString("telephone");
            float java = resultSet.getFloat("java");
            float c = resultSet.getFloat("c");
            float conception = resultSet.getFloat("conception");
            float francais = resultSet.getFloat("francais");
            float anglais = resultSet.getFloat("anglais");

            // Calcule de la moyenne à partir des notes récupérées
            float moyenne = (java + c + conception + francais + anglais) / 5;
            
            // Ajout de la ligne au modèle de tableau
            model.addRow(new Object[]{cinPassport, nom, prenom, age, adress, telephone, java, c, conception, francais, anglais, moyenne});
        }

        // Fermeture de la connexion à la base de données
        connection.close();
    } catch (SQLException ex) {
        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    private void selectAllRows(boolean selected) {
    if (selected) {
        jTable1.selectAll(); // Sélectionne toutes les lignes de la JTable
    } else {
        jTable1.clearSelection(); // Désélectionne toutes les lignes de la JTable
    }
}
/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "cinPassport", "nom", "prenom", "age", "ADD", "phone", "java", "C++", "CONCEPETION", "ANG", "FR", "moyenn", "classement"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField11.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setText("java");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setText("c");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setText("concepetion");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setText("anglais");

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel11.setText("francais");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(55, 55, 55)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField9)
                                    .addComponent(jTextField10)
                                    .addComponent(jTextField11))))))
                .addGap(50, 50, 50))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setText("CIN/passeport");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setText("nom");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setText("prenom");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setText("age");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setText("adresse");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setText("telephone");

        jTextField1.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField2.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField3.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField4.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        jTextField5.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextField6.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(35, 35, 35)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                    .addComponent(jTextField5)
                                    .addComponent(jTextField6)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(80, 80, 80))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                    .addComponent(jTextField2))))))
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/1selected.png"))); // NOI18N
        jLabel19.setText("jLabel19");

        jButton7.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton7.setText("Sélectionner");
        jButton7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reeeeeeeeeeee.png"))); // NOI18N
        jLabel22.setText("jLabel21");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(73, 73, 73)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1035, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(275, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1681, Short.MAX_VALUE))
        );

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ajout-dutilisateur.png"))); // NOI18N
        jLabel15.setText("jLabel15");

        jButton1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton1.setText("Ajouter");
        jButton1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/supprimer-lutilisateur (1).png"))); // NOI18N

        jButton3.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton3.setText("Supprimer");
        jButton3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exportation-de-fichiers.png"))); // NOI18N
        jLabel20.setText("jLabel20");

        jButton5.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton5.setText("exporter (pdf)");
        jButton5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/editer.png"))); // NOI18N

        jButton2.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton2.setText("Modifier");
        jButton2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/tri-des-barres.png"))); // NOI18N
        jLabel18.setText("jLabel18");

        jButton4.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jButton4.setText("Trier");
        jButton4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 4, 1, 4, new java.awt.Color(102, 102, 255)));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/straight-horizontal-line_icon-icons.com_74237.png"))); // NOI18N

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel12.setText("Administrateur");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/aadmin.png"))); // NOI18N
        jLabel13.setText("jLabel13");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(449, 449, 449))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jLabel17))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1035, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 968, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
///////////////////////////////////////////////////////////////////////////////////////////////////////ajouteeeeeeeeeeeeeeeeeeeeeeeeer
   
private void rechercherEtudiantParID(String idRechercheStr) {
    try {
        int idRecherche = Integer.parseInt(idRechercheStr);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int rowCount = model.getRowCount();
        boolean found = false;
        
        for (int i = 0; i < rowCount; i++) {
String idStr = (String) model.getValueAt(i, 0); // Supposons que la colonne 0 contient l'ID sous forme de chaîne
int id = Integer.parseInt(idStr);
            if (id == idRecherche) {
                jTable1.setRowSelectionInterval(i, i); // Sélectionne la ligne trouvée
                found = true;
                break;
            }
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(this, "Aucun étudiant trouvé avec l'ID : " + idRechercheStr, "Étudiant non trouvé", JOptionPane.WARNING_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.", "ID invalide", JOptionPane.WARNING_MESSAGE);
    }
} 
    
    
    private void insererEtudiant(Etudiant etudiant, Connection conn) throws SQLException {
       java.sql.Connection connection = connectionn.getConnection();
        if (connection != null) {
try {
            // Requête SQL pour insérer l'étudiant
            String sql = "INSERT INTO basededonnes (id, nom, prenom, age, adress, telephone, java, c, conception,francais, anglais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Création de la requête préparée
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Remplissage des paramètres de la requête avec les données de l'étudiant
            preparedStatement.setString(1, etudiant.getCinPassport());
            preparedStatement.setString(2, etudiant.getNom());
            preparedStatement.setString(3, etudiant.getPrenom());
            preparedStatement.setInt(4, etudiant.getAge());
            preparedStatement.setString(5, etudiant.getAdresse());
            preparedStatement.setString(6, etudiant.getPhone());
            preparedStatement.setFloat(7, etudiant.getJava());
            preparedStatement.setFloat(8, etudiant.getCPlusPlus());
            preparedStatement.setFloat(9, etudiant.getConception());
            preparedStatement.setFloat(10, etudiant.getAnglais());
            preparedStatement.setFloat(11, etudiant.getFrancais());

            // Exécution de la requête
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Données de l'étudiant enregistrées avec succès !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement des données de l'étudiant : " + ex.getMessage());
        } finally {
            try {
                // Fermeture de la connexion
                connection.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la fermeture de la connexion : " + ex.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Échec de la connexion à la base de données.");
    }
}
    
private void trierParNote() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); // Utilisez le nom correct de votre composant JTable
    int columnIndex = 11; // Remplacez VOTRE_INDICE_DE_COLONNE_NOTE par l'indice de la colonne de note

    // Créer une liste pour stocker les données du tableau
    List<Object[]> rowData = new ArrayList<>();
    for (int i = 0; i < model.getRowCount(); i++) {
        Object[] row = new Object[model.getColumnCount()];
        for (int j = 0; j < model.getColumnCount(); j++) {
            row[j] = model.getValueAt(i, j);
        }
        rowData.add(row);
    }

    // Trier la liste en fonction de la colonne de la note
    Collections.sort(rowData, new Comparator<Object[]>() {
        @Override
        public int compare(Object[] row1, Object[] row2) {
            Comparable<Object> value1 = (Comparable<Object>) row1[columnIndex];
            Comparable<Object> value2 = (Comparable<Object>) row2[columnIndex];
            return value2.compareTo(value1); // Tri décroissant
        }
    });

    // Mettre à jour les données du tableau avec les données triées
    model.setRowCount(0); // Effacer toutes les lignes existantes
    for (Object[] row : rowData) {
        model.addRow(row);
    }
}

    
    
    
    
    
    
    
    
    
    
    
    private void enregistrerDonnes() {
    // Récupérer les valeurs des champs de texte
    String cinPassport = jTextField1.getText();
    String nom = jTextField2.getText();
    String prenom = jTextField3.getText();
    int age = Integer.parseInt(jTextField4.getText());
    String adresse = jTextField5.getText();
    String phone = jTextField6.getText();
    float java = Float.parseFloat(jTextField7.getText());
    float cPlusPlus = Float.parseFloat(jTextField8.getText());
    float conception = Float.parseFloat(jTextField9.getText());
    float anglais = Float.parseFloat(jTextField10.getText());
    float francais = Float.parseFloat(jTextField11.getText());

    // Calculer la moyenne
    float moyenne = (java + cPlusPlus + conception + anglais + francais) / 5;

    // Vérifier que les champs obligatoires ne sont pas vides
    if (!cinPassport.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !adresse.isEmpty() && !phone.isEmpty()) {
        // Vérifier que les valeurs des notes sont valides
        if (java >= 0 && java <= 20 && cPlusPlus >= 0 && cPlusPlus <= 20 && conception >= 0 && conception <= 20 && anglais >= 0 && anglais <= 20 && francais >= 0 && francais <= 20) {
            // Créer l'objet Etudiant avec les valeurs récupérées
            Etudiant etudiant = new Etudiant(cinPassport, nom, prenom, age, adresse, phone, java, cPlusPlus, conception, anglais, francais);

            // Connexion à la base de données et enregistrement de l'étudiant
            try (Connection conn = connectionn.getConnection()) {
                insererEtudiant(etudiant, conn);

                // Actualiser les données dans le modèle de table après l'enregistrement
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[] {cinPassport, nom, prenom, age, adresse, phone, java, cPlusPlus, conception, anglais, francais, moyenne});
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données : " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs numériques valides pour les notes (entre 0 et 20).");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs obligatoires.");
    }
}

///////////////////////////////////////////////////////////////////////2modifier------------------------------------///////////////////////////////////////////////////

    
    
    
    
    // Méthode pour récupérer les informations d'un étudiant à partir de la base de données
private Etudiant recupererEtudiant(String id, Connection conn) throws SQLException {
    Etudiant etudiant = null;
    String sql = "SELECT * FROM basededonnes WHERE id=?";
    PreparedStatement preparedStatement = conn.prepareStatement(sql);
    preparedStatement.setString(1, id);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
        // Création de l'objet Etudiant avec les informations récupérées
        etudiant = new Etudiant(
            resultSet.getString("id"),
            resultSet.getString("nom"),
            resultSet.getString("prenom"),
            resultSet.getInt("age"),
            resultSet.getString("adress"),
            resultSet.getString("telephone"),
            resultSet.getFloat("java"),
            resultSet.getFloat("c"),
            resultSet.getFloat("conception"),
            resultSet.getFloat("francais"),
            resultSet.getFloat("anglais")
        );
    }
    return etudiant;
}

// Méthode pour afficher les informations d'un étudiant dans les champs de texte
private void afficherInformationsEtudiant(Etudiant etudiant) {
    jTextField2.setText(etudiant.getNom());
    jTextField3.setText(etudiant.getPrenom());
    jTextField4.setText(String.valueOf(etudiant.getAge()));
    jTextField5.setText(etudiant.getAdresse());
    jTextField6.setText(etudiant.getPhone());
    jTextField7.setText(String.valueOf(etudiant.getJava()));
    jTextField8.setText(String.valueOf(etudiant.getCPlusPlus()));
    jTextField9.setText(String.valueOf(etudiant.getConception()));
    jTextField10.setText(String.valueOf(etudiant.getAnglais()));
    jTextField11.setText(String.valueOf(etudiant.getFrancais()));
}





// Méthode pour mettre à jour les informations d'un étudiant dans la base de données
private void mettreAJourEtudiant(Etudiant etudiant, Connection conn) throws SQLException {
    String sql = "UPDATE basededonnes SET nom=?, prenom=?, age=?, adress=?, telephone=?, java=?, c=?, conception=?, francais=?, anglais=? WHERE id=?";
    PreparedStatement preparedStatement = conn.prepareStatement(sql);
    preparedStatement.setString(1, etudiant.getNom());
    preparedStatement.setString(2, etudiant.getPrenom());
    preparedStatement.setInt(3, etudiant.getAge());
    preparedStatement.setString(4, etudiant.getAdresse());
    preparedStatement.setString(5, etudiant.getPhone());
    preparedStatement.setFloat(6, etudiant.getJava());
    preparedStatement.setFloat(7, etudiant.getCPlusPlus());
    preparedStatement.setFloat(8, etudiant.getConception());
    preparedStatement.setFloat(9, etudiant.getFrancais());
    preparedStatement.setFloat(10, etudiant.getAnglais());
    preparedStatement.setString(11, etudiant.getCinPassport());
    preparedStatement.executeUpdate();
    JOptionPane.showMessageDialog(null, "Données de l'étudiant mises à jour avec succès !");}

    


    
    
    
  
    
    
    
    
    
    

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
          // Récupérer les nouvelles valeurs des champs de texte
         // TODO add your handling code here:
  try {
    // Récupérer les valeurs des champs de texte
    String id = jTextField1.getText();
    String nom = jTextField2.getText();
    String prenom = jTextField3.getText();
    int age = Integer.parseInt(jTextField4.getText());
    String adresse = jTextField5.getText();
    String phone = jTextField6.getText();
    float java = Float.parseFloat(jTextField7.getText());
    float cPlusPlus = Float.parseFloat(jTextField8.getText());
    float conception = Float.parseFloat(jTextField9.getText());
    float anglais = Float.parseFloat(jTextField10.getText());
    float francais = Float.parseFloat(jTextField11.getText());

    // Construire la requête SQL pour mettre à jour les informations de l'étudiant
    String sql = "UPDATE basededonnes SET nom = ?, prenom = ?, age = ?, adress = ?, telephone = ?, java = ?, c = ?, conception = ?, francais = ?, anglais = ? WHERE id = ?";
    
    // Établir la connexion à la base de données et préparer la requête
    try (Connection conn = connectionn.getConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {

        // Définir les valeurs des paramètres dans la requête
        statement.setString(1, nom);
        statement.setString(2, prenom);
        statement.setInt(3, age);
        statement.setString(4, adresse);
        statement.setString(5, phone);
        statement.setFloat(6, java);
        statement.setFloat(7, cPlusPlus);
        statement.setFloat(8, conception);
        statement.setFloat(9, francais);
        statement.setFloat(10, anglais);
        statement.setString(11, id);

        // Exécuter la requête SQL
        int rowsAffected = statement.executeUpdate();

        // Vérifier si la mise à jour a réussi
        if (rowsAffected > 0) {
            // Afficher un message de succès
            JOptionPane.showMessageDialog(null, "Les modifications ont été enregistrées avec succès.");
            
            // Actualiser le tableau
            remplir();
        } else {
            // Afficher un message d'erreur si la mise à jour a échoué
            JOptionPane.showMessageDialog(null, "La mise à jour a échoué.");
        }
    }
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Erreur lors de la modification : " + e.getMessage());
    System.err.println(e);
}
   
                                            

      
      
      
      
      
      
      
      
      
    }//GEN-LAST:event_jButton2ActionPerformed
//////////////////////////////////////ajouter button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        enregistrerDonnes();
        ((DefaultTableModel) jTable1.getModel()).fireTableDataChanged();
    }//GEN-LAST:event_jButton1ActionPerformed
/////////////////////////////////////////////////////////////////////trierrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
   
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        // Récupérer l'identifiant de l'étudiant à modifier à partir de jTextField1
    String id = jTextField1.getText();
    
    // Vérifier si l'identifiant est non vide
    if (!id.isEmpty()) {
        // Connexion à la base de données et récupération des informations de l'étudiant
        try (Connection conn = connectionn.getConnection()) {
            // Récupérer les informations de l'étudiant à partir de la base de données
            Etudiant etudiantAModifier = recupererEtudiant(id, conn);
            if (etudiantAModifier != null) {
                // Afficher les informations de l'étudiant dans les champs de texte
                afficherInformationsEtudiant(etudiantAModifier);
            } else {
                JOptionPane.showMessageDialog(null, "Étudiant non trouvé !");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données : " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez saisir l'identifiant de l'étudiant à modifier.");
    }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        trierParNote(); // Appeler la fonction de tri lorsque le bouton est cliqué
  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
         int row = jTable1.getSelectedRow(); 
    if (row != -1) { // Vérifier si une ligne est sélectionnée
        // Récupérer l'ID sous forme de String depuis la jTable1
        String idString = (String) jTable1.getValueAt(row, 0);
        // Convertir l'ID en entier
        int id = Integer.parseInt(idString);

        // Requête de suppression dans la base de données
        String query = "DELETE FROM basededonnes  WHERE id = ?";
        
        try (java.sql.Connection connection = connectionn.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, id); // Remplacer le paramètre ? dans la requête par l'ID
            
            int rowsAffected = statement.executeUpdate(); // Exécuter la requête de suppression
            
            if (rowsAffected > 0) {
                // Si la suppression a réussi, actualisez le tableau ou affichez un message de succès
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(row); // Supprimer la ligne du modèle de tableau
            } else {
                // Affichez un message d'erreur si aucune ligne n'a été supprimée
                JOptionPane.showMessageDialog(null, "La suppression a échoué.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        // Si aucune ligne n'est sélectionnée, affichez un message pour informer l'utilisateur
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne à supprimer.");
    }
    
        
        
        
        
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

  extrairePDF();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        
                                        
        login loginInterface = new login();
        loginInterface.setVisible(true);
        this.dispose(); 
    
        
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
