import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String pathDir = "D://Games//savegames//";
        String zipFile = "zip_output.zip";

        openZip(pathDir + zipFile, pathDir);

        GameProgress gameProgress = openProgress(pathDir + "new_save2.dat");
        System.out.println(gameProgress.toString());
    }

    private static void openZip(String fileZip, String pathDir) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(fileZip))) {
            ZipEntry zipEntry;
            String name;
            while ((zipEntry = zin.getNextEntry()) != null) {
                name = zipEntry.getName();

                FileOutputStream fout = new FileOutputStream(pathDir + "new_" + name);

                int c;
                while ((c = zin.read()) != -1) {
                    fout.write(c);
                }
                fout.flush();

                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static GameProgress openProgress(String fileUnZip) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(fileUnZip);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return gameProgress;
    }
}
