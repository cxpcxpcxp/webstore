import com.search.WebStoreSearchApplication;
import com.search.client.GoodsClient;
import com.search.pojo.Goods;
import com.search.repository.GoodsRepository;
import com.search.service.GoodsService;
import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Spu;
import com.webstore.item.pojo.SpuBo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebStoreSearchApplication.class)
public class QueryDataTest {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    GoodsClient goodsClient;

    @Autowired
    GoodsService goodsService;

    @Test
    public void queryDataTest(){
        Integer page = 1;
        Integer rows = 100;
        do {
//            querySpuBoByPage(null, true, page, rows);
            // 分批查询spuBo
            PageResult<SpuBo> pageResult = this.goodsClient.findSpuByPage(null, true, page, rows);
            // 遍历spubo集合转化为List<Goods>
            List<Goods> goodsList = pageResult.getItems().stream().map(spuBo -> {
                try {
                    return this.goodsService.buildGoods((Spu) spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            this.goodsRepository.saveAll(goodsList);
            // 获取当前页的数据条数，如果是最后一页，没有100条
            rows = pageResult.getItems().size();
            // 每次循环页码加1
            page++;
        } while (rows == 100);
    }
}