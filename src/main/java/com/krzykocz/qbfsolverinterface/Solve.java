/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krzykocz.qbfsolverinterface;

import com.sun.java_cup.internal.runtime.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import sun.misc.IOUtils;



/**
 *
 * @author krzykocz
 */
public class Solve {
    
    
    
    public String solverDep(File qbfinput) {
        try {
            String jarDir  = new File (getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
            Process depqbf = Runtime.getRuntime().exec(jarDir+"/lib/depqbf -v -v" + " " + qbfinput);
            depqbf.waitFor();
            String depInput = new BufferedReader(new InputStreamReader(depqbf.getInputStream())).lines().collect(Collectors.joining("\n"));
            String depError = new BufferedReader(new InputStreamReader(depqbf.getErrorStream())).lines().collect(Collectors.joining("\n"));
            return depError + "\n" + depInput;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(Solve.class.getName()).log(Level.SEVERE, null, ex);
            //System.err.println(ex);
        }
        return null;
    }
    
    public String solverRare(File qbfinput) {
        try {
            String jarDir  = new File (getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
            Process rareqs = Runtime.getRuntime().exec(jarDir+"/lib/rareqs" + " " + qbfinput);
            rareqs.waitFor();
            String rareInput = new BufferedReader(new InputStreamReader(rareqs.getInputStream())).lines().collect(Collectors.joining("\n"));
            String rareError = new BufferedReader(new InputStreamReader(rareqs.getErrorStream())).lines().collect(Collectors.joining("\n"));
            return rareError + "\n" + rareInput;
        } catch (IOException | InterruptedException ex) {
            //Logger.getLogger(Solve.class.getName()).log(Level.SEVERE, null, ex);
            //System.err.println(ex);
        }
        return null;
    }
}
