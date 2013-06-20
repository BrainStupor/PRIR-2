import java.io.*;

public class GAParams implements Serializable{
	int subjectlist[] = new int[6];
	int teacherlist[] = new int[24];
	int popsize;
	int ngen;
	double pmut;
	double pcross;
	double best_fitness[] = new double[1];
	int teacher_ids[] = new int[400];
	int room_ids[] = new int[400];
	int class_ids[] = new int[400];
	int subject_ids[] = new int[400];
}
