package com.example.carsalesapp.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.carsalesapp.dao.CarInformationDao;
import com.example.carsalesapp.model.CarInformation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {CarInformation.class},
        version = 2,
        exportSchema = false)
public abstract class CarInformationDb extends RoomDatabase {
    public abstract CarInformationDao carDaoAccess();

    private static volatile com.example.carsalesapp.database.CarInformationDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static com.example.carsalesapp.database.CarInformationDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarInformationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), com.example.carsalesapp.database.CarInformationDb.class, "carDescriptions")
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE cars");
        }
    };

    private static RoomDatabase.Callback sRoomDatabaseCallback =  new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                CarInformationDao dao = INSTANCE.carDaoAccess();

                CarInformation car = new CarInformation("BMW 326", "Kaunas", 2100.0, null, null);
                dao.insert(car);
                car = new CarInformation("Seat Leon", "Vilnius", 300.0, null, null);
                dao.insert(car);
            });
        }
    };
}
