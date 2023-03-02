package course.ExceptionPkg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionSampleTest {
    @Test
    void shouldReturnAdd(){
        RuntimeException t=Assertions.assertThrows(RuntimeException.class, ()->ExceptionSample.add(-1001,1000));
      assertEquals("Number Must be greater equals or greater than -1000",t.getMessage());
      RuntimeException s=Assertions.assertThrows(RuntimeException.class,()->ExceptionSample.add(50001,100));
      assertEquals("Number Must be less than 50000",s.getMessage());
      assertEquals(400,ExceptionSample.add(100,300));

    }

}