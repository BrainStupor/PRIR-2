import java.io.FileWriter;
import java.io.FileReader;
import java.io.Serializable;
import java.io.IOException;
import java.rmi.*;
import java.util.Scanner;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class Client implements Serializable{
	public static void main(String args[]){
		System.setProperty("java.security.policy", "file:///home/stud0/i0barnus/prircpy/java.policy");
		GAParams params = new GAParams();
		/*try{
			loadGAParams("config.txt", params);
			loadSubjectParams("subjects.txt", params);
			loadTeacherParams("teachers.txt", params);
		}
		catch(IOException ex){
			System.out.println("Blad podczas odczytywania parametrow!");
			ex.printStackTrace();
		}*/
		
		//parametry
		params.popsize = 200;
		params.ngen = 1000;
		params.pcross = 0.6;
		params.pmut = 0.001;
		params.subjectlist = new int[]{2, 5, 7, 8, 6, 8};
		params.teacherlist = new int[]{3, -1, -1, 5, -1, -1, 0, 2, -1, 2, 4, -1, 0, 5, -1, 1, 3, -1, 2, 4, -1, 0, 1, 4};
		/*
		3 -1 -1
		5 -1 -1
		0 2 -1
		2 4 -1
		0 5 -1
		1 3 -1
		2 4 -1
		0 1 4
		*/
		
		//uruchomienie
		try {
			if(System.getSecurityManager() == null){
				System.setSecurityManager(new RMISecurityManager());
			}
			Registry registry = LocateRegistry.getRegistry(Integer.valueOf(args[0]));
	    		GAInterface stub = (GAInterface) registry.lookup("Server");
			
			params = stub.remote_runGA(params);
	    		    		
    		printIndividual("najlepszy.txt", params);
	    		
		} catch (Exception e) {
		    	System.err.println("Client exception: " + e.toString());
		    	e.printStackTrace();
		}
		//
		
	}
	
	
		public static void loadGAParams(String fname, GAParams params) throws IOException{
			Scanner scan = new Scanner(new FileReader(fname));
			params.popsize = scan.nextInt();
			params.ngen = scan.nextInt();
			params.pcross = scan.nextDouble();
			params.pmut = scan.nextDouble();
		}
		
		public static void loadSubjectParams(String fname, GAParams params)throws IOException{
			Scanner scan = new Scanner(new FileReader(fname));
			for(int i = 0; i < 6; ++i){
				params.subjectlist[i] = scan.nextInt();
			}
		}
			
		public static void loadTeacherParams(String fname, GAParams params)throws IOException{
			Scanner scan = new Scanner(new FileReader(fname));
			for(int i = 0; i < 8; ++i){
				for(int j = 0; j < 3; ++j){
					params.teacherlist[i*3 + j] = scan.nextInt();
				}
			}
			
		}
		
		public static void printIndividual(String fname, GAParams params)throws IOException{
			System.out.println("Dostosowanie najlepszego osobnika: " + params.best_fitness[0] + "/5000");
			FileWriter out = new FileWriter(fname);
			out.write("Oznaczenia: (id_przedmiotu, id_klasy, id_nauczyciela)\n");
			for(int i = 0; i < 5; ++i){
				out.write("Dzien " + (i+1) + ":\n");
				for(int j = 0; j < 8; ++j){
					out.write("Lekcja #" + (j+1) + ":\t");
					for(int k = 0; k < 10; ++k){
						if(params.class_ids[(i*8+j)*10 + k] != -1){
							out.write( "(" + params.subject_ids[(i*8+j)*10 + k] + "," + params.class_ids[(i*8+j)*10 + k] + "," + params.teacher_ids[(i*8+j)*10 + k] + ")\t");
						}
						else{
							out.write("(WOLNE)\t");
						}
					}
					out.write("\n");
				}
				out.write("\n");
			}
		}
	}
	
	

/*fprintf(out, "Oznaczenia: (id_przedmiotu, id_klasy, id_nauczyciela)\n");
	int i,j,k;
	for(i = 0; i < DAYS; ++i){
		fprintf(out, "Dzien %d:\n", i+1);
		for(j = 0; j < PERIODS_PER_DAY; ++j){
			fprintf(out, "Lekcja #%d:\t", j+1);
			for(k = 0; k < CLASSROOMS; ++k){
				if(ind->genotype[i*PERIODS_PER_DAY + j][k].class_id != NONE)
					fprintf(out, "(%d,%d,%d)\t", ind->genotype[i*PERIODS_PER_DAY + j][k].subject_id, ind->genotype[i*PERIODS_PER_DAY + j][k].class_id, ind->genotype[i*PERIODS_PER_DAY + j][k].teacher_id);
				else
					fprintf(out, "(WOLNE)\t");
			}
			fprintf(out, "\n");
		}
		fprintf(out, "\n");
	}
	fclose(out);*/
