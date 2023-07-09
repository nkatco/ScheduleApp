package com.example.scheduleapp;

import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Less;
import com.example.scheduleapp.model.Lesson;
import com.example.scheduleapp.model.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainRepository {

    private static List<Group> groups = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    private static List<Cab> cabs = new ArrayList<>();

    private static List<Group> favoriteGroups;
    private static List<Teacher> favoriteTeachers;
    private static List<Cab> favoriteCabs;
    private String domain = "https://asu.samgk.ru/";
    private String domain2 = "https://mfc.samgk.ru/";

    public MainRepository() throws IOException, JSONException {
        final String[] lineGroup = new String[1];
        final String[] lineTeachers = new String[1];
        final String[] lineCabs = new String[1];

        final JSONArray[] arrayGroups = {null};
        final JSONArray[] arrayTeachers = {null};
        final JSONObject[] arrayCabs = {null};

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lineGroup[0] = getGroups();
                    arrayGroups[0] = new JSONArray(lineGroup[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < arrayGroups[0].length(); i++) {
                    try {
                        JSONObject obj = arrayGroups[0].getJSONObject(i);
                        Group group = new Group(obj.getInt("id"), obj.getString("name"), false);
                        groups.add(group);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lineTeachers[0] = getTeachers();
                    arrayTeachers[0] = new JSONArray(lineTeachers[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < arrayTeachers[0].length(); i++) {
                    try {
                        JSONObject obj = arrayTeachers[0].getJSONObject(i);
                        Teacher teacher = new Teacher(obj.getInt("id"), obj.getString("name"), false);
                        teachers.add(teacher);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lineCabs[0] = getCabs();
                    arrayCabs[0] = new JSONObject(lineCabs[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                JSONArray names = arrayCabs[0].names();
                for (int i = 0; i < names.length(); i++) {
                    try {
                        Cab cab = new Cab(names.getString(i), arrayCabs[0].getString(names.getString(i)), false);
                        cabs.add(cab);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread[] threads = new Thread[]{thread1, thread2, thread3};
        threads[0].start();
        threads[1].start();
        threads[2].start();

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<Cab> getAllCabs() {
        return cabs;
    }
    public List<Group> getAllGroups() {
        return groups;
    }
    public List<Teacher> getAllTeachers() {
        return teachers;
    }

    private String getCabs() throws IOException {
        URL url = new URL(domain + "/api/cabs");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        return line;
    }
    private String getGroups() throws IOException {
        URL url = new URL(domain2 + "api/groups");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        return line;
    }
    private String getTeachers() throws IOException {
        URL url = new URL(domain + "api/teachers");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        return line;
    }
    public List<Lesson> getScheduleByGroup(int id, String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(domain + "api/schedule/" + id + "/" + data + "/")
                .addHeader("Referer", "https://samgk.ru")
                .build();

        Response response = client.newCall(request).execute();
        String line = response.body().string();

        List<Lesson> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(line);
            String date = object.getString("date");
            JSONArray lessons = object.getJSONArray("lessons");
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject obj = lessons.getJSONObject(i);
                list.add(new Lesson(date, new Less(obj.getString("title"),
                        obj.getInt("num"),
                        obj.getString("teachername"),
                        obj.getString("nameGroup"),
                        obj.getString("cab"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Lesson> getScheduleByTeacher(int id, String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(domain + "api/schedule/teacher/" + data + "/" + id)
                .addHeader("Referer", "https://samgk.ru")
                .build();

        Response response = client.newCall(request).execute();
        String line = response.body().string();

        List<Lesson> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(line);
            String date = object.getString("date");
            JSONArray lessons = object.getJSONArray("lessons");
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject obj = lessons.getJSONObject(i);
                list.add(new Lesson(date, new Less(obj.getString("title"),
                        obj.getInt("num"),
                        obj.getString("teachername"),
                        obj.getString("nameGroup"),
                        obj.getString("cab"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Lesson> getScheduleByCab(String cab, String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(domain + "api/schedule/cabs/" + data + "/cabNum/" + cab)
                .addHeader("Referer", "https://samgk.ru")
                .build();

        Response response = client.newCall(request).execute();
        String line = response.body().string();


        List<Lesson> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(line);
            String date = object.getString("date");
            JSONArray lessons = object.getJSONArray("lessons");
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject obj = lessons.getJSONObject(i);
                list.add(new Lesson(date, new Less(obj.getString("title"),
                        obj.getInt("num"),
                        obj.getString("teachername"),
                        obj.getString("nameGroup"),
                        obj.getString("cab"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    public static void updateFavoriteGroups() {
        favoriteGroups = SplashScreenActivity.getSavedGroups();
        int a = 0;
        Collections.sort(groups, new Comparator<Group>() {
            @Override
            public int compare(Group group, Group t1) {
                return group.getName().compareTo(t1.getName());
            }
        });
        for (int i = 0; i < groups.size(); i++) {
            for (Group group1 : favoriteGroups) {
                if(groups.get(i).getName().equals(group1.getName()) && groups.get(i).getId() == group1.getId()) {
                    groups.get(i).setFavorite(true);
                    Collections.swap(groups, a, i);
                    a++;
                }
            }
        }
    }
    public static void updateFavoriteTeachers() {
        favoriteTeachers = SplashScreenActivity.getSavedTeachers();
        int a = 0;
        Collections.sort(teachers, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher teacher, Teacher t1) {
                return teacher.getName().compareTo(t1.getName());
            }
        });
        for (int i = 0; i < teachers.size(); i++) {
            for (Teacher teacher1 : favoriteTeachers) {
                if(teachers.get(i).getName().equals(teacher1.getName()) && teachers.get(i).getId() == teacher1.getId()) {
                    teachers.get(i).setFavorite(true);
                    Collections.swap(teachers, a, i);
                    a++;
                }
            }
        }
    }
    public static void updateFavoriteCabs() {
        favoriteCabs = SplashScreenActivity.getSavedCabs();
        Collections.sort(cabs, new Comparator<Cab>() {
            @Override
            public int compare(Cab cab, Cab t1) {
                return cab.getName().compareTo(t1.getName());
            }
        });
        int a = 0;
        for (int i = 0; i < cabs.size(); i++) {
            for (Cab cab1 : favoriteCabs) {
                if(cabs.get(i).getCabNum().equals(cab1.getCabNum()) && cabs.get(i).getName().equals(cab1.getName())) {
                    cabs.get(i).setFavorite(true);
                    Collections.swap(cabs, a, i);
                    a++;
                }
            }
        }
    }
}
