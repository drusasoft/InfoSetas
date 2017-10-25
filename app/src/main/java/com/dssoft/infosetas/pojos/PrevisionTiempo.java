package com.dssoft.infosetas.pojos;

/**
 * Created by Angel on 13/10/2017.
 */


//Clase en la que se mapean los datos devueltos por el WS Tiempo
public class PrevisionTiempo
{

    private String cod;
    private City city;
    private java.util.List<List> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    //**********************************************************************************************
        //Defincion de las Clases internas necesarias para el correcto Mapeo del Objeto Json devuelto por el Servidor
    //**********************************************************************************************

    //En esta clase se mapea la informacion del Objeto Json city
    public class City
    {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    //En esta clase se mapea la informacion del Objeto Json list
    public class List
    {

        private long dt;
        private Temp temp;
        private String humidity;
        private java.util.List<Weather> weather;


        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public java.util.List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(java.util.List<Weather> weather) {
            this.weather = weather;
        }
    }


    //En esta clase se mapea la informacion del Objeto Json temp
    public class Temp
    {

        private String min;
        private String max;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }
    }


    //En esta clase se mapea la informacion del Objeto Json weather
    public class Weather
    {

        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }


}
