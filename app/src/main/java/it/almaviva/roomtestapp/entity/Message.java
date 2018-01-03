package it.almaviva.roomtestapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import it.almaviva.roomtestapp.utils.DateTypeConverter;

/**
 * Created by m.spalletti on 03/01/2018.
 */
@Entity(indices = @Index("timeStamp"))
@TypeConverters(DateTypeConverter.class)
public class Message {
	@PrimaryKey(autoGenerate = true)
	public long id;
	public Date timeStamp;
	public String body;
}
