package com.cheng.budgetplanner.db;



final class Tables {
    public static String[] allTables;

    static
    {
        allTables = new String[] { Bills.tableName};

    }

    public Tables() {
        super();
    }


    public static final class Bills {
        public static final String _id = "_id";
        public static final String tableName = "Bills";
        public static final String definition = "create table Bills(_id integer primary key autoincrement, id integer, cost REAL, content text, userId integer, payName text, payImg text, payid integer, sortid integer, crdate integer, income integer, sortName text, sortImg text)";

        public static final String id = "id";
        public static final String cost = "cost";
        public static final String content = "content";
        public static final String userid = "userid";
        public static final String payName = "payName";
        public static final String payImg = "payImg";
        public static final String payid = "payid";
        public static final String sortid = "sortid";
        public static final String crdate = "crdate";
        public static final String income = "income";
        public static final String sortName = "sortName";
        public static final String sortImg = "sortImg";
    }
}
