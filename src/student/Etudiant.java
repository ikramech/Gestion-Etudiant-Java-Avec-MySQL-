
package student;

/**
 *
 * @author ikram
 */
public class Etudiant {
    private String cinPassport;
    private String nom;
    private String prenom;
    private String adresse;
    private String phone;
    private float java;
    private float cPlusPlus;
    private int age;
    private float conception;
    private float anglais;
    private float francais;

    public Etudiant(String cinPassport, String nom, String prenom, int age, String adresse, String phone, float java, float cPlusPlus, float conception, float anglais, float francais) {
        this.cinPassport = cinPassport;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.phone = phone;
        this.java = java;
        this.cPlusPlus = cPlusPlus;
     
        this.conception = conception;
        this.anglais = anglais;
        this.francais = francais;
    }

    // Getters
    public String getCinPassport() {
        return cinPassport;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhone() {
        return phone;
    }

    public float getJava() {
        return java;
    }

    public float getCPlusPlus() {
        return cPlusPlus;
    }

    
   

    public float getConception() {
        return conception;
    }

    public float getAnglais() {
        return anglais;
    }

    public float getFrancais() {
        return francais;
    }
    public int getAge() {
        return age;
    }
}


