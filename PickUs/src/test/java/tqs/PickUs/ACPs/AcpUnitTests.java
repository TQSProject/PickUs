package tqs.PickUs.ACPs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tqs.PickUs.entities.ACP;


public class AcpUnitTests {
    @Test
    public void testEquals()
    {
        ACP acp1 = new ACP("Fnac Aveiro", "Aveiro");
        acp1.setId(1);
        ACP acp2 = new ACP("Fnac Aveiro", "Aveiro");
        acp2.setId(1);
        ACP acp3 = new ACP("Worten Aveiro", "Aveiro");

        Assertions.assertEquals(acp1, acp2);
        Assertions.assertNotEquals(acp1, acp3);
    }

    @Test
    public void testHashCode()
    {
        ACP acp1 = new ACP("Fnac Aveiro", "Aveiro");
        acp1.setId(1);
        ACP acp2 = new ACP("Fnac Aveiro", "Aveiro");
        acp2.setId(1);
        ACP acp3 = new ACP("Worten Aveiro", "Aveiro");

        Assertions.assertEquals(acp1.hashCode(), acp2.hashCode());
        Assertions.assertNotEquals(acp1.hashCode(), acp3.hashCode());
    }
}
