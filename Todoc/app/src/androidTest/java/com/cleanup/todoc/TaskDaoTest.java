package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Project;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    //private SaveMyTodocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    /*
    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                SaveMyTodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
    */


    /*
    @After
    public void closeDb() throws Exception {
        database.close();
    }

     */


    // DATA SET FOR TEST
    //private static long PROJECT_ID = 4;
    //private static Project PROJECT_DEMO = new Project(4, "Projet 4", 125 );

    /*
    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
    //    this.database.projectDao().createProject(PROJECT_DEMO);
        // TEST
    //    Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));

      //  assertTrue(project.getName().equals(PROJECT_DEMO.getName()));
        assertTrue("1".equals("1"));
    }

     */
}
