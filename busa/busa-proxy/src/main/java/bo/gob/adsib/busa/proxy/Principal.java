/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.adsib.busa.proxy;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author jmedina
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Principal {
    public static void main (String [] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException
    {
        SpringApplication.run(Principal.class, args);
    }
}
