package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowV2RenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.tl.source.MyDataModel;

/**
 * @author Sayi
 */
public class XWPFTemplateTest {

    TableRenderData table;

    @BeforeEach
    public void init() {
        RowV2RenderData header = Rows.of(new TextRenderData("FFFFFF", "Word处理解决方案"),
                new TextRenderData("FFFFFF", "是否跨平台"), new TextRenderData("FFFFFF", "易用性")).bgColor("ff9800").create();
        RowV2RenderData row0 = Rows.of("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装").create();
        RowV2RenderData row1 = Rows.of("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装").create();
        RowV2RenderData row2 = Rows.of("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构").create();
        RowV2RenderData row3 = Rows.of("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API").create();
        RowV2RenderData row4 = Rows.of("Jacob、winlib", "Windows平台", "复杂，不推荐使用").create();
        table = Tables.of(header, row0, row1, row2, row3, row4).create();
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderMap() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("header", "Deeply love what you love.");
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("what",
                        "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", new HyperLinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));

                put("solution_compare", table);

                put("feature", new NumberingRenderData(
                        new TextRenderData("Plug-in grammar, add new grammar by yourself"),
                        new TextRenderData(
                                "Supports word text, local pictures, web pictures, table, list, header, footer..."),
                        new TextRenderData("Templates, not just templates, but also style templates")));
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/template.docx").render(datas);

        FileOutputStream out = new FileOutputStream("out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @Test
    public void testRenderObject() throws Exception {
        MyDataModel obj = new MyDataModel();
        obj.setHeader("Deeply love what you love.");
        obj.setName("Poi-tl");
        obj.setWord("模板引擎");
        obj.setTime("2019-05-31");
        obj.setWhat(
                "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
        obj.setAuthor("Sayi卅一");
        obj.setIntroduce("http://www.deepoove.com");
        obj.setPortrait(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        obj.setSolutionCompare(table);
        obj.setFeature(new NumberingRenderData(new TextRenderData("Plug-in grammar, add new grammar by yourself"),
                new TextRenderData("Supports word text, local pictures, web pictures, table, list, header, footer..."),
                new TextRenderData("Templates, not just templates, but also style templates")));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/template.docx").render(obj);

        FileOutputStream out = new FileOutputStream("out_template_object.docx");
        template.write(out);
        template.close();
        out.flush();
        out.close();
    }
}
