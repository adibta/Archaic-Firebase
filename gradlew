package id.go.dprd_katingan.e_lapor.data.database;

import android.database.Cursor;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfReport;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfReport;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public ReportDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReport = new EntityInsertionAdapter<Report>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `reports`(`id`,`user_id`,`content`,`image_link`,`category`,`latitude`,`longitude`,`geo_location`,`timestamp`,`profileUrl`,`profileName`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Report value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserId());
        }
        if (value.getContent() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContent());
        }
        if (value.getImageLink() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageLink());
        }
        stmt.bindLong(5, value.getCategory());
        stmt.bindDouble(6, value.getLatitude());
        stmt.bindDouble(7, value.getLongitude());
        if (value.getGeoLocation() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getGeoLocation());
        }
        stmt.bindLong(9, value.getTimestamp());
        if (value.getProfileUrl() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getProfileUrl());
        }
        if (value.getProfileName() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getProfileName());
        }
      }
    };
    this.__deletionAdapterOfReport = new EntityDeletionOrUpdateAdapter<Report>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `reports` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Report value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM reports";
        return _query;
      }
    };
  }

  @Override
  public void insertReport(Report report) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReport.insert(report);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(List<Report> reportList) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfReport.insert(reportList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteReport(Report report) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfReport.handle(report);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Report> getReports() {
    final String _sql = "SELECT * FROM reports";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfContent = _cursor.getColumnIndexOrThrow("content");
      final int _cursorIndexOfImageLink = _cursor.getColumnIndexOrThrow("image_link");
      final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("