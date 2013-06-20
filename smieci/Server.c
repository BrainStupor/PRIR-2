#include <jni.h>
#include "Server.h"
#include "TimetableGA.h"	
	


JNIEXPORT jint JNICALL Java_Server_runGA
  (	JNIEnv * env, jobject object, jintArray subjectlist, jintArray teacherlist, 
	jint popsize, jint ngen, jdouble pmut, jdouble pcross, 
	jdoubleArray best_fitness, jintArray teacher_ids, jintArray room_ids, jintArray class_ids, jintArray subject_ids){


	jint *sl = (*env)->GetIntArrayElements(env, subjectlist, 0);
	jint *tl = (*env)->GetIntArrayElements(env, teacherlist, 0);
	jdouble *bstfit = (*env)->GetDoubleArrayElements(env, best_fitness, 0);
	jint *t_id = (*env)->GetIntArrayElements(env, teacher_ids, 0);
	jint *r_id = (*env)->GetIntArrayElements(env, room_ids, 0);
	jint *c_id = (*env)->GetIntArrayElements(env, class_ids, 0);
	jint *s_id = (*env)->GetIntArrayElements(env, subject_ids, 0);
			
	srand(time(0));
	struct TimetableGA ga;	
	
	int i,j;
	
	initsl(&subject_list);
	for(i = 0; i < SUBJECTS; ++i){
		subject_list.hours[i] = sl[i];
	}
	
	inittl(&teacher_list);
	for(i = 0; i < TEACHERS; ++i){
		for(j = 0; j < MAX_SUBJECTS_PER_TEACHER; ++j){
			teacher_list.subjects[i][j] = tl[i*MAX_SUBJECTS_PER_TEACHER + j];
		}
	}
	
	
	initClassList(&class_list);

	
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
	*bstfit = ga.population[0].fitness;
	
	
	for(i = 0; i < DAYS*PERIODS_PER_DAY; ++i){
		for(j = 0; j < CLASSROOMS; ++j){
			t_id[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].teacher_id;
			r_id[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].room_id;
			c_id[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].class_id;
			s_id[i*CLASSROOMS + j] = ga.population[0].genotype[i][j].subject_id;
		}
	}
	
	finalizeGA(&ga);
	freeClassList(&class_list);
	freesl(&subject_list);
	freetl(&teacher_list);




	(*env)->ReleaseIntArrayElements( env, subjectlist, sl,0);
	(*env)->ReleaseIntArrayElements(env, teacherlist, tl, 0);
	(*env)->ReleaseDoubleArrayElements(env, best_fitness, bstfit, 0);
	(*env)->ReleaseIntArrayElements(env, teacher_ids, t_id, 0);
	(*env)->ReleaseIntArrayElements(env, room_ids, r_id, 0);
	(*env)->ReleaseIntArrayElements(env, class_ids, c_id, 0);
	(*env)->ReleaseIntArrayElements(env, subject_ids, s_id, 0);





}

