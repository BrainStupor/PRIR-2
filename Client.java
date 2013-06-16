import java.io.*;

public class Client{
	public static void main(String args[]){
		GAParams params = new GAParams();
		try{
			loadGAParams("config.txt", params);
			loadSubjectParams("subjects.txt", params);
			loadTeacherParams("teachers.txt", params);
		}
		catch(IOException ex){
			System.out.println("B³¹d podczas odczytywania parametrów!");
			ex.printStackTrace();
		}
		
		//uruchomienie
		
		//
		printIndividual(params);
	}
	
	
		public static void loadGAParams(String fname, GAParams params){
			Scanner scan = new Scanner(new FileReader(fname));
			params.popsize = scan.nextInt();
			params.ngen = scan.nextInt();
			params.pcross = scan.nextDouble();
			params.pmut = scan.nextDouble();
		}
		
		public static void loadSubjectParams(String fname, GAParams params){
			Scanner scan = new Scanner(new FileReader(fname));
			for(int i = 0; i < 6; ++i){
				params.subjectlist[i] = scan.nextInt();
			}
		}
			
		public static void loadTeacherParams(String fname, GAParams params){
			Scanner scan = new Scanner(new FileReader(fname));
			for(int i = 0; i < 8; ++i){
				for(int j = 0; j < 3; ++j){
					params.teacherlist[i*3 + j] = scan.nextInt();
				}
			}
			
		}
		
		public static void printIndividual(String fname, GAParams params){
			System.out.println("Dostosowanie najlepszego osobnika: " + params.best_fitness[0] + "/5000");
			FileWriter out = new Filewriter(fname);
			out.print("Oznaczenia: (id_przedmiotu, id_klasy, id_nauczyciela)\n");
			for(int i = 0; i < 5; ++i){
				out.print("Dzien " + (i+1) + ":\n");
				for(int j = 0; j < 8; ++j){
					out.print("Lekcja #" + (j+1) + ":\t");
					for(int k = 0; k < 10; ++k){
						if(params.class_ids[(i*8+j)*10 + k] != -1){
							out.print( "(" + params.subject_ids[(i*8+j)*10 + k] + "," + params.class_ids[(i*8+j)*10 + k] + "," + params.teacher_ids[(i*8+j)*10 + k] + ")\t");
						}
						else{
							out.print("(WOLNE)\t");
						}
					}
					out.print("\n");
				}
				out.print("\n");
			}
		}
	}
	
	
};

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
