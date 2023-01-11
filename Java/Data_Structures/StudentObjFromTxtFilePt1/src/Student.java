class Student {

        protected String StudentID;
        protected int ScoreTest1;
        protected int ScoreTest2;
        protected int ScoreTest3;
        protected String StudentName;
        protected int testPercent;
        protected String letterGrade;
        //Constructor for class Student
        public Student(String StudentID, String StudentName, int ScoreTest1, int ScoreTest2, int ScoreTest3){
            this.StudentID = StudentID;
            this.StudentName = StudentName;
            this.ScoreTest1 = ScoreTest1;
            this.ScoreTest2 = ScoreTest2;
            this.ScoreTest3 = ScoreTest3;
        }

        public void percentTestScore(){
            testPercent = (int) ((ScoreTest1 + ScoreTest2 + ScoreTest3) / 3.0);
        }

        public void calcLetterGrade(){
            if (testPercent >= 90){

                letterGrade = "A";

            }else if(testPercent >= 80 ){

                letterGrade = "B";

            }else if(testPercent >= 70){

                letterGrade = "C";

            }else if(testPercent >= 60){

                letterGrade = "D";

            }else {
                letterGrade = "F";
            }
        }

        public String getStudentID(){
            return StudentID;
        }

        public String getStudentName(){
            return StudentName;
        }

        public int getScoreTest1(){
            return ScoreTest1;
        }

        public int getScoreTest2(){
            return ScoreTest2;
        }

        public int getScoreTest3(){
            return ScoreTest3;
        }

        public void setTestPercent(int testPercent){
            this.testPercent = testPercent;
        }

        public int getTestPercent(){
            return testPercent;
        }

        public void setLetterGrade(String letterGrade){
            this.letterGrade = letterGrade;
        }

        public String getLetterGrade(){
            return letterGrade;
        }


}//end of class Student
