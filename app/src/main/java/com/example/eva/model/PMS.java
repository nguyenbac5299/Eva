package com.example.eva.model;

public class PMS {

    public static class symptom {
        public static final int GOOD = 0;
        public static final int CRAMP = 1;// CHUỘT RÚT
        public static final int ACNE = 2;
        public static final int STOMACHACHE = 3; //ĐAU BỤNG
        public static final int HEADACHE = 4; //ĐAU ĐẦU
        public static final int DIZZINESS = 5; //CHÓNG MẶT
        public static final int FLATULENCE = 6;// ĐẦY HƠI
        public static final int BACKACHE = 7;//ĐAU LƯNG
        public static final int CHESTPAIN = 8; //ĐAU NGỰC
        public static final int NECKPAIN = 9; //ĐAU CỔ
        public static final int SHOULDEPAIN = 10; //ĐAU VAI
        public static final int NAUSE = 11; //BUỒN NÔN
    }

    public static class HealthyState {
        public static int weight;
        public static int temperature;
    }

    public static class Medicine {
        public static boolean medicine;
    }

    public static class Sporting {
        public static final int NOSPORTING = 0;
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
