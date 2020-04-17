package repo;

import entity.Chart;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChartRepositoryTest {

    @Test
    void create() {
        ChartRepository chartRepository = new ChartRepository();

        Chart chart = new Chart("Top 100 Test Songs");

        chartRepository.create(chart);

        Chart chartFinded = chartRepository.findById(chart.getId());

        Assert.assertEquals(chart.getId(), chartFinded.getId());
    }

    @Test
    void findById() {
        ChartRepository chartRepository = new ChartRepository();

        Chart chart = chartRepository.findById(1);

        Assert.assertEquals(1, chart.getId());
    }

    @Test
    void findByName() {
        ChartRepository chartRepository = new ChartRepository();

        List<Chart> chartList = chartRepository.findByName("Top 100 KissFM");

        for(Chart chart : chartList){
            Assert.assertEquals("Top 100 KissFM", chart.getName());
        }
    }
}