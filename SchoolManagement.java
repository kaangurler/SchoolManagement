import java.util.Scanner;

public class SchoolManagement {
    static Scanner scanner = new Scanner(System.in);
    static String[][] students = new String[5][4];

    public static void main(String[] args) {
        programMenu();
    }

    /**
     * Ana Menüyü çalıştırır
     */
    private static void programMenu() {
        int choise;
        do {
            System.out.print(
                    "1. Öğrenci Ekle\n" +
                            "2. Öğrenci Listele\n" +
                            "3. Öğrenci Notu Güncelle\n" +
                            "4. Ortalama Hesapla\n" +
                            "5. Öğrenci Sil\n" +
                            "6. Belirli Notun Altındaki Öğrencileri Listele\n" +
                            "0. Çıkış\n" +
                            "Lütfen Yapmak İstediğiniz İşlemi Seçiniz: "
            );
            switch (choise = scanner.nextInt()) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    calculateAverageGrade();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    displayAllStudentsBelowGrade();
                    break;
                case 0:
                    System.out.println("Program Sonlandırıldı");
                    break;
                default:
                    System.out.println("Lütfen 0 ile 6 arasında bir sayı giriniz");
                    break;
            }
            System.out.println("-".repeat(50));
        } while (choise != 0);
    }

    /**
     * Listeye öğrenci ekler
     */
    private static void addStudent() {
        int index = firstNullIndex();
        System.out.println("---Öğrenci Ekle---");
        if (index < students.length && index != -1) {
            students[index][0] = String.valueOf(index);
            scanner.nextLine();
            System.out.print("İsim= ");
            students[index][1] = scanner.nextLine();
            System.out.print("Ders= ");
            students[index][2] = scanner.nextLine();
            int grade;
            do {
                System.out.print("Not= ");
                if (!gradeCheck(grade = scanner.nextInt())) {
                    System.out.println("Lütfen 0-100 arasında bir değer giriniz");
                } else {
                    students[index][3] = String.valueOf(grade);
                }
            } while (!gradeCheck(grade));

        } else {
            System.out.println("Kontenjan Dolu");
        }
    }

    /**
     * Kayıtlı tüm öğrencilerin bilgilerini gösterir
     */
    private static void displayAllStudents() {
        System.out.printf("%-30s%-30s%-30s%-30s%n", "ID", "İsim", "Ders", "Not");
        for (String[] student : students) {
            if (student[0] != null) {
                System.out.printf("%-30s%-30s%-30s%-30s%n", student[0], student[1], student[2], student[3]);
            }
        }
    }

    /**
     * Parametre olarak verilen grade değerinin altındaki tüm öğrencileri gösterir
     * @param grade Student grade
     */
    private static void displayAllStudents(int grade) {
        System.out.printf("%-30s%-30s%-30s%-30s%n", "ID", "İsim", "Ders", "Not");
        for (String[] student : students) {
            if (student[0] != null && Integer.parseInt(student[3]) < grade) {
                System.out.printf("%-30s%-30s%-30s%-30s%n", student[0], student[1], student[2], student[3]);
            }
        }
    }

    /**
     *  Kullanıcıdan bir not değeri alarak bu notun altındaki tüm öğrencileri gösterir
     */
    private static void displayAllStudentsBelowGrade() {
        int grade;
        do {
            System.out.print("Not giriniz: ");
            grade = scanner.nextInt();
            if (gradeCheck(grade)) {
                displayAllStudents(grade);
            } else {
                System.out.println("Not 0-100 arasında bir değer giriniz");
            }
        } while (!gradeCheck(grade));
    }

    /**
     * Kayıtlı bir öğrencinin notunu günceller
     */
    private static void updateGrade() {
        System.out.print("Öğrencinin id'sini giriniz: ");
        int id = scanner.nextInt();
        int index = findIndexById(id);
        if (index >= 0) {
            int grade;
            do {
                System.out.print("Öğrencinin notunu giriniz: ");
                grade = scanner.nextInt();
                System.out.println(grade < 0 || grade > 100 ? "Lütfen 0-100 arası bir değer girin": "Not Güncellendi");
            } while (grade < 0 || grade > 100);
            students[index][3] = String.valueOf(grade);
        } else {
            System.out.println("Öğrenci bulunamadı");
        }
    }

    /**
     * Kayıtlı tüm öğrencilerin not ortalamasını gösterir
     */
    private static void calculateAverageGrade() {
        double sum = 0;
        double countElement = 0;
        for (String[] student : students) {
            if (student[3] != null) {
                sum += Double.parseDouble(student[3]);
                countElement++;
            }
        }
        System.out.println("Ortalama Not= " + (sum / countElement));
    }

    /**
     * Array'den öğrenci silme
     */
    private static void removeStudent() {
        System.out.print("Öğrencinin id'sini giriniz: ");
        int id = findIndexById(scanner.nextInt());
        if (id != -1) {
            students[id][0] = null;students[id][1] = null;students[id][2] = null;students[id][3] = null;
        } else {
            System.out.println("Öğrenci bulunamadı");
        }
    }

    /**
     * Bir öğrencinin id'sine göre array'deki index'ini bulur
     * @param id Student id
     * @return Eğer kayıtlı öğrenciler arasında bulunduysa index'i döndürür, bulunamazsa -1 döndürür
     */
    private static int findIndexById(int id) {
        for (int i = 0; i < students.length; i++) {
            if (students[i][0] != null && (Integer.parseInt(students[i][0])) == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Parametre olarak aldığı grade değerinin 0-100 arasında olup olmadığını kontrol eder
     * @param grade Student grade
     * @return grade 0-100 arasındaysa true, değilse false döndürür
     */
    private static boolean gradeCheck(int grade) {
        return grade <= 100 && grade >= 0;
    }

    /**
     * Student array'inin içindeki null olan ilk index'i döndürür
     * @return Eğer null bulunursa index, bulunamazsa -1 döndürür
     */
    private static int firstNullIndex() {
        for (int i = 0; i < students.length; i++) {
            if (students[i][0] == null) {
                return i;
            }
        }
        return -1;
    }
}
