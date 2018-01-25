import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.*;
import java.security.*;
import java.io.*;
import java.util.Scanner;


public class AESEncryption {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in); 
      AESEncryption aes = new AESEncryption();
      try {   
         boolean on = true;
         
         while (on) {
            System.out.print("-----------------Enter-----------------\n\"E\" to encrypt\n\"D\" to decrypt\n\"0\" to exit\n\nEnter: ");
            String mode = input.next();
            
            if (mode.equals("E")) {
               aes.encrypt();
            }
            else if (mode.equals("D")) {
               aes.decrypt();
            }
            else if (mode.equals("0")){
               System.exit(0);
            }
            else {
               System.out.println("Invalid selection!\n");
            }
         }         
         

      }
      catch (NoSuchAlgorithmException ex1) {
         System.out.println("Sorry. No such algorithm");
      }
      catch (NoSuchPaddingException ex2) {
         System.out.println("Sorry. No such padding");
      }
      catch (InvalidKeyException ex3) {
         System.out.println("Sorry. Invalid key");
      }
      catch (FileNotFoundException ex4) {
         System.out.println("Sorry. File was not found!");
      }
      catch (IOException ex5) {
         System.out.println("Sorry. IOException!");
      }
      catch (IllegalBlockSizeException ex6) {
         System.out.println("Sorry. Block size is illegal!");
      }
      catch (BadPaddingException ex7) {
         System.out.println("Sorry. Padding is BAD!");
      }
      
   }
   
// data.txt --> input.read(byte[]) --> byte[] >>> encoded byte[] --> output.write(encoded byte[]) --> encryptedData.txt
   public void encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
    FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException {
      Scanner input = new Scanner(System.in); 
      System.out.print("Enter the name of the file you wish to encrypt: ");
      String fileToEncrypt = input.next();
      
      Cipher cipher = Cipher.getInstance("AES");
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");         
      //initialize keygen with int keysize parameter
      keyGen.init(128);
      SecretKey key = keyGen.generateKey();
      cipher.init(Cipher.ENCRYPT_MODE, key);
                                                                                 
//copy data to byte[] b
      File file = new File(fileToEncrypt);
      FileInputStream fileInput = new FileInputStream(file);
      //File.length returns long, but byte array parameter needs integer
      byte[] b = new byte[(int)file.length()];
      //reads b.length bytes of data FROM data.txt TO byte[] b, puts bytes into the array 
      fileInput.read(b);
      fileInput.close();
//encode byte[] b and output TO encryptedData.txt    
      byte[] encodedByteArr = cipher.doFinal(b);
      FileOutputStream fileOutput = new FileOutputStream("encryptedData.txt");
//write TO encryptedData.text FROM encodedByteArr 
      fileOutput.write(encodedByteArr);
      fileOutput.close();
      
                                                                                 
//encode secret key and put in byte[] encodedKey and write to key.txt
      byte[] encodedKey = key.getEncoded();
      FileOutputStream keyFileOutput = new FileOutputStream("key.txt");
      keyFileOutput.write(encodedKey);
      keyFileOutput.close();
   }
   
   
   public void decrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
    FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException {
      Scanner input = new Scanner(System.in); 
      System.out.print("Enter the name of the file you wish to decrypt: ");
      String fileToDecrypt = input.next();
      System.out.print("Enter the name of the KEY file: ");
      String secretKey = input.next();
   
   //read from encryptedKeyFile, which contains byte[] encodedKey
      File encryptedKeyFile = new File(secretKey);        
      FileInputStream keyInput = new FileInputStream(encryptedKeyFile);
      
   //create new byte[] to store encoded key bytes
      byte[] keyBytes = new byte[(int)encryptedKeyFile.length()];
   //read FROM key.txt TO byte[] keyBytes     
      keyInput.read(keyBytes);
      keyInput.close();

   //construct new secret key using encoded key byte[] keyBytes      
      SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

   //create new cipher object instance to decrypt, using AES param      
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, key);

   //find encryptedData.      
      File encryptedData = new File(fileToDecrypt);
      FileInputStream fileInput = new FileInputStream(encryptedData);
      byte[] encryptedByte = new byte[(int)encryptedData.length()];
   //read FROM encryptedData.txr TO encodeByte
      fileInput.read(encryptedByte);
      fileInput.close();
      
   //decrypte byte[] into new byte[] and write to decryptedData.t
      byte[] decryptedByte = cipher.doFinal(encryptedByte);
      FileOutputStream fileOuput = new FileOutputStream("decryptedData.txt");
      fileOuput.write(decryptedByte);
      fileOuput.close();
   }
}