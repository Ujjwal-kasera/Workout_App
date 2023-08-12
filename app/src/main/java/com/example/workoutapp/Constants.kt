package com.example.workoutapp

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks=ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val highKnees=ExerciseModel(
            2,
            "High Knees",
            R.drawable.ic_high_knees,
            false,
            false
        )
        exerciseList.add(highKnees)

        val lunge=ExerciseModel(
            3,
            "Lunges",
            R.drawable.ic_lunge,
            false,
            false
        )
        exerciseList.add(lunge)

        val tricepDips=ExerciseModel(
            4,
            "Tricep Dips",
            R.drawable.ic_tricep_dip,
            false,
            false
        )
        exerciseList.add(tricepDips)

        val pushUp=ExerciseModel(
            5,
            "Push Up",
            R.drawable.ic_push_up,
            false,
            false
        )
        exerciseList.add(pushUp)

        val pushUpAndRotation=ExerciseModel(
            6,
            "PushUp And Rotation",
            R.drawable.ic_push_up_and_rotation,
            false,
            false
        )
        exerciseList.add(pushUpAndRotation)

        val abdominalCrunch=ExerciseModel(
            7,
            "Abdominal Crunches",
            R.drawable.ic_abdominal_crunch,
            false,
            false
        )
        exerciseList.add(abdominalCrunch)

        val stepUpOntoChair=ExerciseModel(
            8,
            "Step Up",
            R.drawable.ic_step_up_onto_chair,
            false,
            false
        )
        exerciseList.add(stepUpOntoChair)

        val squat=ExerciseModel(
            9,
            "Jumping Jacks",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val plank=ExerciseModel(
            10,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val sidePlank=ExerciseModel(
            11,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlank)

        val wallSit=ExerciseModel(
            12,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            false,
            false
        )
        exerciseList.add(wallSit)

        return exerciseList
    }
}