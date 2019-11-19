package com.example.hw3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class PersistentObject {
    public abstract void Insert(SQLiteDatabase database);
    public abstract void InitFrom(Cursor cursor, SQLiteDatabase database);
}
