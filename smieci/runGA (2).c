#include <jni.h>
#include "runGA.h"
#include "TimetableGA.h"	
	
int runGA(	int * subjectlist, int * teacherlist, 
			int popsize, int ngen, double pmut, double pcross, 
			double * best_fitness, int * teacher_ids, int * room_ids, int * class_ids, int * subject_ids);

JNIEXPORT jint JNICALL Java_runGA_runGA
  (	JNIEnv * env, jobject object, jintArray subjectlist, jintArray teacherlist, 
	jint popsize, jint ngen, jdouble pmut, jdouble pcross, 
	jdoubleArray best_fitness, jintArray teacher_ids, jintArray room_ids, jintArray class_ids, jintArray subject_ids){
			
	jint *sl = (*env)->GetIntArrayElements(env, subjectlist, 0);
	jint *tl = (*env)->GetIntArrayElements(env, teacherlist, 0);
	jdouble *bstfit = (*env)->GetDoubleArrayElements(env, best_finess, 0);
	jint *t_id = (*env)->GetIntArrayElements(env, teacher_ids, 0);
	jint *r_id = (*env)->GetIntArrayElements(env, room_ids, 0);
	jint *c_id = (*env)->GetIntArrayElements(env, class_ids, 0);
	jint *s_id = (*env)->GetIntArrayElements(env, subject_ids, 0);
	
	int errorcode = runGA(sl, tl, popsize, ngen, pmut, pcross, bstfit, t_id, r_id, c_id, s_id);
	
	(*env)->ReleaseIntArrayElements( env, subjectlist,0);
	(*env)->ReleaseIntArrayElements(env, teacherlist, 0);
	(*env)->ReleaseDoubleArrayElements(env, best_finess, 0);
	(*env)->ReleaseIntArrayElements(env, teacher_ids, 0);
	(*env)->ReleaseIntArrayElements(env, room_ids, 0);
	(*env)->ReleaseIntArrayElements(env, class_ids, 0);
	(*env)->ReleaseIntArrayElements(env, subject_ids, 0);
	
	return( errorCode );
}

