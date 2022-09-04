package com.udacity.asteroidradar.database

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.DATABASE_NAME
import com.udacity.asteroidradar.Constants.TABLE_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow




@Dao
interface AsteroidDoa {

    @Query("SELECT * FROM ${TABLE_NAME} ORDER by closeApproachDate")
    fun getAll(): Flow<List<Asteroid>>


    @Query("SELECT * FROM ${TABLE_NAME} where closeApproachDate>= :startDate and closeApproachDate<=:endDate ORDER by closeApproachDate")
    fun get7Day(startDate :String,endDate:String): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<Asteroid>)

    @Delete
    fun delete(asteroid: Asteroid)


}

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDoa
    companion object {
        @Volatile
        private lateinit var instance: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if(!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DATABASE_NAME)
                        .build()
                }
            }
            return instance
        }
    }
}
