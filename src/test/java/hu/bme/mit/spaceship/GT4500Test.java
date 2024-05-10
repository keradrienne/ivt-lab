package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class GT4500Test {

  private GT4500 ship;

  TorpedoStore mockPrimary;
  TorpedoStore mockSecondary;

  @BeforeEach
  public void init(){
    //when(mockPrimary.fire(1)).thenReturn(true);
    //when(mockSecondary.fire(1)).thenReturn(true);
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange

    when(mockPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty(){
    // Arrange

    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_SecondaryEmpty(){
    // Arrange

    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);
    when(mockPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(0)).fire(1);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_BothEmpty(){
    // Arrange

    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_BothEmpty_SecondaryFiredLast(){
    // Arrange

    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);

    // Assert
    assertEquals(false, result);
  }

}
