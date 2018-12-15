package archivation;

import archivation.arithmetic.ArithmeticCompressor;
import archivation.huffman.HuffmanAlgorithm;
import archivation.huffman.Symbol;
import com.sun.source.doctree.InlineTagTree;
import javafx.collections.transformation.SortedList;
import utils.CharacterFrequencyCounter;
import utils.nums_compresors.ZigZagVagIntConverter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


/*
* SecureRandom Algorithms:
    NativePRNG
    DRBG
    SHA1PRNG
    NativePRNGBlocking
    NativePRNGNonBlocking

   Ciphers:
   RSA
DES
DESede
DESedeWrap
PBEWithMD5AndDES
PBEWithMD5AndTripleDES
PBEWithSHA1AndDESede
PBEWithSHA1AndRC2_40
PBEWithSHA1AndRC2_128
PBEWithSHA1AndRC4_40
PBEWithSHA1AndRC4_128
PBEWithHmacSHA1AndAES_128
PBEWithHmacSHA224AndAES_128
PBEWithHmacSHA256AndAES_128
PBEWithHmacSHA384AndAES_128
PBEWithHmacSHA512AndAES_128
PBEWithHmacSHA1AndAES_256
PBEWithHmacSHA224AndAES_256
PBEWithHmacSHA256AndAES_256
PBEWithHmacSHA384AndAES_256
PBEWithHmacSHA512AndAES_256
Blowfish
AES
AES_128/ECB/NoPadding
AES_128/CBC/NoPadding
AES_128/OFB/NoPadding
AES_128/CFB/NoPadding
AES_128/GCM/NoPadding
AES_192/ECB/NoPadding
AES_192/CBC/NoPadding
AES_192/OFB/NoPadding
AES_192/CFB/NoPadding
AES_192/GCM/NoPadding
AES_256/ECB/NoPadding
AES_256/CBC/NoPadding
AES_256/OFB/NoPadding
AES_256/CFB/NoPadding
AES_256/GCM/NoPadding
AESWrap
AESWrap_128
AESWrap_192
AESWrap_256
RC2
ARCFOUR
*
* */
public class LetsTry {
    private static char[] password = new char[]{'a', 's', 'd', 'A', 'S', 'D', '1', '2', '3'};
    private static Path workDir = Paths.get("/home/denis", "lisp/tmp2");

    public static void main(String[] args) {
        /*for (Provider provider: Security.getProviders()) {
            System.out.println(provider.getName());
            for (Provider.Service service: provider.getServices()) {

                if (service.getType().equals("CertificateFactory")) {
                    System.out.println(service.getAlgorithm());
                }
            }

            System.out.println();
        }*/


        int i = -1;
        int y = 0;
        while (i != 0) {
            i >>>= 1;
            y++;
        }
        System.out.println(y);

        /*
        try {
            //String test = "aadfs";
            String test = "akkadddd";
            ByteArrayInputStream fis = new ByteArrayInputStream(test.getBytes());
            //FileOutputStream fos = new FileOutputStream(workDir.resolve("someFile").toFile());
            FileOutputStream fos = new FileOutputStream(workDir.resolve("someFile").toFile());
            CharacterFrequencyCounter cfc = new CharacterFrequencyCounter(new InputStreamReader(new ByteArrayInputStream(test.getBytes())));
            //fis = new ByteArrayInputStream("olooolloofasd\nghgj".getBytes());
            Map<Character, Integer> table = cfc.count();
            //ArithmeticCompressor ac = new ArithmeticCompressor(new FileInputStream(workDir.resolve("someFile").toFile()), (TreeMap<Character, Integer>) table, fos);
            //ac.decompress();
            ArithmeticCompressor ac = new ArithmeticCompressor(fis, (TreeMap<Character, Integer>) table, fos);
            ac.compress();

            fos = new FileOutputStream(workDir.resolve("result").toFile());
            ArithmeticCompressor ad = new ArithmeticCompressor(new FileInputStream(workDir.resolve("someFile").toFile()), (TreeMap<Character, Integer>) table, fos);
            ad.decompress();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            KeyStore store = KeyStore.getInstance(Paths.get("/home/denis", ".keystore").toFile(), password);
            //KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            //generator.initialize(2048);
            //KeyPair pair = generator.generateKeyPair();
            //System.out.println(Base64.getEncoder().encodeToString(store.getKey("testpair", password).getEncoded()));
            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry)store.getEntry("ttp2", new KeyStore.PasswordProtection(password));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, keyEntry.getPrivateKey());
            FileOutputStream initVector = new FileOutputStream(workDir.resolve("file").toFile());

            FileInputStream fis = new FileInputStream(workDir.resolve("someFile").toFile());
            FileOutputStream fos = new FileOutputStream(workDir.resolve("tmpOutput").toFile());
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            InputToOutput ito = new InputToOutput(fis, cos);
            ito.transfer();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

       /* try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            KeyPair keys = generator.generateKeyPair();
            PrivateKey priv = keys.getPrivate();
            PublicKey pub = keys.getPublic();
            KeyStore.getInstance().setKeyEntry();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/


      /*  Path path = Paths.get(System.getProperty("user.dir"), "tmp_files");
        Path file = path.resolve("file");
        try {
            FileInputStream fis = new FileInputStream(file.toFile());
            Cipher cipher = Cipher.getInstance("RSA/CBE");
            cipher.init(Cipher.ENCRYPT_MODE, );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/


        /*
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] buff = new byte[3];
            fis.read(buff);
            md.update(buff);

            fis.read(buff);
            md.update(buff);
           // md.update(fis.readAllBytes());

            System.out.println(Arrays.toString(md.digest(fis.readAllBytes())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
