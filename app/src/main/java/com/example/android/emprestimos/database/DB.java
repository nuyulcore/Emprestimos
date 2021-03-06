package com.example.android.emprestimos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.emprestimos.models.Loan;
import com.example.android.emprestimos.models.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheusmanoel on 21/02/17.
 */

public class DB {
    private SQLiteDatabase db;

    public DB(Context ctx) {
        DBCore dbCore = DBCore.getInstance(ctx);
        db = dbCore.getWritableDatabase();
    }

    public void insertPerson(Person person) {
        ContentValues values = new ContentValues();

        values.put("name", person.getName());
        values.put("email", person.getEmail());
        values.put("phone", person.getPhone());

        db.insert("people", null, values);
    }


    public void updatePerson(Person person) {
        ContentValues values = new ContentValues();

        values.put("name", person.getName());
        values.put("email", person.getEmail());
        values.put("phone", person.getPhone());

        db.update("people", values, "_id = ?", new String[]{""+person.getId()});
    }

    public void deletePerson(Person person) {
        db.delete("people", "_id = ?", new String[]{""+person.getId()});
    }

    public List<Person> queryPeople(String selection, String[] selectionArgs) {
        List people = new ArrayList<Person>();
        String[] columns = new String[]{"_id", "name", "email", "phone"};

        //// TODO: 21/02/17 query asc by name
        Cursor cursor = db.query("people", columns, selection, selectionArgs, null, null, null,
                null);

        if(cursor.moveToFirst()) {

            do {

                Person p = new Person(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3));
                people.add(p);

            } while(cursor.moveToNext());
        }

        cursor.close();

        return people;
    }

    public void insertLoan(Loan loan) {
        ContentValues values = new ContentValues();

        values.put("date", loan.getDate());
        values.put("devolutionDate", loan.getDevolutionDate());
        values.put("isMoney", loan.isMoney());
        values.put("value", loan.getValue());
        values.put("loan_person", loan.getPerson().getId());
        values.put("description", loan.getDescription());

        db.insert("loans", null, values);
    }
}
