package com.example.eva.model;

public class PMS {

    public static class symptom {
        public static final int GOOD = 0;
        public static final int ACNE = 1;
        public static final int STOMACHACHE = 2; //ĐAU BỤNG
        public static final int HEADACHE = 3; //ĐAU ĐẦU
        public static final int DIZZINESS = 4; //CHÓNG MẶT
        public static final int BLOADTING = 5;// ĐẦY HƠI
        public static final int BACKACHE = 6;//ĐAU LƯNG
        public static final int BREAST_PAIN= 7; //ĐAU NGỰC
        public static final int NAUSEA = 8; //BUỒN NÔN
    }

    public static class HealthyState {
        public static int weight;
        public static int temperature;
    }

    public static class Medicine {
        public static boolean medicine;
    }

    public static class Sporting {
        public static final int NO_SPORTING = 0;
        public static final int RUN = 1;
        public static final int CYCLING = 2;
        public static final int GYM = 3;
        public static final int SWIMMING = 4;
        public static final int AEROBICS = 5;

    }

    public static class Feeling {
        public static final int NORMAL = 0;
        public static final int HAPPY = 1;
        public static final int SAD = 2;
        public static final int ANGRY = 3;
        public static final int WORRY = 4;
        public static final int TIRED = 5;
        public static final int FEAR = 6;
    }

    public static class Menstruation {
        public static final int MEDIUM = 0;
        public static final int MUCH = 1;
        public static final int LITTLE = 2;
    }

    public class Note {
        String note;
    }


}
