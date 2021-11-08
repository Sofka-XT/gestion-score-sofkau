package co.com.sofka.wsscore.infra.model;

import java.util.List;

public  class DataResponse {
        private  Integer draw;
        private  Integer recordsTotal;
        private List<List<String>> data;

         public Integer getDraw() {
             return draw;
         }

         public void setDraw(Integer draw) {
             this.draw = draw;
         }

         public Integer getRecordsTotal() {
             return recordsTotal;
         }

         public void setRecordsTotal(Integer recordsTotal) {
             this.recordsTotal = recordsTotal;
         }

         public List<List<String>> getData() {
             return data;
         }

         public void setData(List<List<String>> data) {
             this.data = data;
         }
     }