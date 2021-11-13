package com.example.carsalesapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.carsalesapp.dao.CarInformationDao;
import com.example.carsalesapp.model.CarInformation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CarInformation.class}, version = 1, exportSchema = false)
public abstract class CarInformationDb extends RoomDatabase {

    public abstract CarInformationDao carDaoAccess();

    private static volatile CarInformationDb INSTANCE;
    private static final int NUMBER_OF_THREADS =4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CarInformationDb getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (CarInformationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(), CarInformationDb.class, "cars")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =  new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CarInformationDao dao = INSTANCE.carDaoAccess();

                CarInformation car = new CarInformation("326", "BMW", "Leaking oil", 2100.0);
                dao.insert(car);
                car = new CarInformation("leon", "seat", "Check engine light on", 300.0);
                dao.insert(car);
            });
        }
    };
}
