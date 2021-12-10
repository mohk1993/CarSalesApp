package com.example.carsalesapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.carsalesapp.converters.Converters;
import com.example.carsalesapp.dao.CarInfoDao;
import com.example.carsalesapp.model.CarEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CarEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CarDb extends RoomDatabase {

    public abstract CarInfoDao carDaoAccess();

    private static volatile CarDb INSTANCE;
    private static final int NUMBER_OF_THREADS =4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CarDb getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (CarInfoDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(), CarDb.class, "cars2")
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
                CarInfoDao dao = INSTANCE.carDaoAccess();

                CarEntity car = new CarEntity("326", "BMW", "Leaking oil", 2100.0,"admin@admin",null);
                dao.insert(car);
                car = new CarEntity("leon", "seat", "Check engine light on", 300.0,"user@user",null);
                dao.insert(car);
            });
        }
    };
}

