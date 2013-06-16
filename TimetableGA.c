#include "TimetableGA.h"	
	
int runGA(	int * subjectlist, int * teacherlist, 
			int popsize, int ngen, double pmut, double pcross, 
			double * best_fitness, int * teacher_ids, int * room_ids, int * class_ids, int * subject_ids){
			
	srand(time(0));
	struct TimetableGA ga;	
	
	int i,j;
	
	initSubjectList(&subject_list);
	for(i = 0; i < SUBJECTS; ++i){
		subject_list.hours[i] = subjectlist[i];
	}
	
	initTeacherList(&teacher_list);
	for(i = 0; i < TEACHERS; ++i){
		for(j = 0; j < MAX_SUBJECTS_PER_TEACHER; ++j){
			teacher_list.subjects[i][j] = teacherlist[i*MAX_SUBJECTS_PER_TEACHER + j];
		}
	}
	
	
	initClassList(&class_list);
	for(i = 0; i < CLASSES; ++i){
		for(j = 0; j < SUBJECTS; ++j){
			class_list.teachers[i][j] = classlist[i*SUBJECTS + j];
		}
	}
	
	initGA(&ga, popsize,ngen,pcross,pmut);
	
	setSelection(&ga, &tournamentSelection);
	setCrossover(&ga, &singlePointCrossover);
	
	int gen;
	double prevfit=0,nextfit=0;
	for(gen = 0; gen < ga.ngen; ++gen){
		nextGen(&ga);
		
		nextfit = ga.population[0].fitness;
		if(nextfit == MAX_FIT)
			break;	
		
		prevfit = nextfit; 		
	}
	
	fitness(&(ga.population[0]));
	*best_fitness = ga.population[0].fitness;
	
	
	for(i = 0; i < DAYS*PERIODS_PER_DAY; ++i){
		for(j = 0; j < CLASSROOMS; ++j){
			teacher_ids[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].teacher_id;
			room_ids[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].room_id;
			class_ids[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].class_id;
			subject_ids[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].subject_id;
		}
	}
	
	finalizeGA(&ga);
	freeClassList(&class_list);
	freeSubjectList(&subject_list);
	freeTeacherList(&teacher_list);
}

