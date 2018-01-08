/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krzykocz.qbfsolverinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krzykocz
 */
public class ConvertHToCnf {

    private String[] variables;
    private String clauses;
    private File qbfinput;
    private Integer used = 0;

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public void setClauses(String clauses) {
        this.clauses = clauses;
    }

    public File getQbfinput() {
        return qbfinput;
    }

    public void setQbfinput(File qbfinput) {
        this.qbfinput = qbfinput;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public void convertToCnf() {
        try {
//            Map with variables numeration
            Map<String, String> variablesMap = new HashMap<>();
            variablesMap.put("-", "-");

//            Put variables numeration to variablesMap
            StringBuilder variablesCnf = new StringBuilder();
            Integer variablesCount = 0;
            for (int i = 0; i < variables.length; i++) {
                switch (variables[i]) {
                    case "A":
                        variablesCnf.append("a ");
                        break;
                    case "E":
                        variablesCnf.append("e ");
                        break;
                    case "R":
                        variablesCnf.append("r ");
                        break;
                    default:
                        variablesMap.put(variables[i], (++variablesCount).toString() + " ");
                        variablesCnf.append(variablesMap.get(variables[i]));
                        //variablesCnf.append(" ");
                        if (variables[i] == variables[variables.length - 1] || "A".equals(variables[i + 1]) || "E".equals(variables[i + 1]) || "R".equals(variables[i + 1])) {
                            variablesCnf.append("0\n");
                        }
                        break;
                }
            }

//            Convert clauses to CNF format
            clauses = clauses.replace("-", "- ");
            clauses = clauses.replace("\n", " \n ");
            String[] clausesArray = clauses.split(" ");
            StringBuilder clausesCnf = new StringBuilder();
            Integer clausesCount = 0;
            for (String x : clausesArray) {
                if ("\n".equals(x)) {
                    clausesCount++;
                    clausesCnf.append("0\n");
                } else if (variablesMap.get(x) != null) {
                    clausesCnf.append(variablesMap.get(x));
                }
            }
            clausesCnf.append("0");
            clausesCount++;
            qbfinput = File.createTempFile("qbf", ".cnf");

            try (BufferedWriter writer = Files.newBufferedWriter(qbfinput.toPath())) {
                writer.write("p cnf " + variablesCount + " " + clausesCount + "\n");
                writer.write(variablesCnf.toString());
                writer.write(clausesCnf.toString());
            }

        } catch (IOException ex) {
            //Write code to show Error alert
            //Logger.getLogger(ConvertHToCnf.class.getName()).log(Level.SEVERE, null, ex);
            deleteQbfInput();
        }

    }

    public void deleteQbfInput() {
        if (qbfinput.exists()) {
            qbfinput.delete();
        }
    }
    
    public String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String text;
            while ((text = reader.readLine()) != null) {
                sb.append(text).append("\n");
            }
            return sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(ConvertHToCnf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
