import java.io.*;

public class GAParams implements Serializable{
	int subjectlist[];
	int teacherlist[];
	int popsize;
	int ngen;
	double pmut;
	double pcross;
	double best_fitness[];
	int teacher_ids[];
	int room_ids[];
	int class_ids[];
	int subject_ids[];
}