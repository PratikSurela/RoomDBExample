package pratik.com.myapplication.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pratik.com.myapplication.Model.Profile;

@Dao
public interface DatabaseDao {
    @Insert
    void insert(RegisterEntity task);

    @Query("SELECT * FROM RegisterEntity where username LIKE :strUsername")
    Profile getUserFromUsername(String strUsername);
}