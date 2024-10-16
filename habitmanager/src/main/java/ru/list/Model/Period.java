package ru.list.Model;

/**
 * перечисление описывает период повторения привычки
 */
public enum Period implements PeriodDescription{

    /**
     * ежедневно
     */
    daily
    {
        public String getDescription() {
            return "Ежедневно";
        }
        public int getInterval() {
            return 1;
        }
        
    }, 
    /**
     * еженедельно
     */     
    weekly
    {
        public String getDescription() {
            return "Еженедельно";
        }
        public int getInterval() {
            return 7;
        }
        
    }, 
    /**
     * ежемесячно
     */
    monthly
    {
        public String getDescription() {
            return "Ежемесячно";
        }
        public int getInterval() {
            return 30;
        }
        
    }, 
    /**
     * ежегодно
     */
    annually
    {
        public String getDescription() {
            return "Ежегодно";
        }
        public int getInterval() {
            return 365;
        }
        
    };
}
