package pizzashop.model;


public class Payment {

    private Integer tableNumber;
    private PaymentType type;
    private Double amount;
    public Payment(int tableNumber, PaymentType type, Double amount) {
        this.tableNumber = tableNumber;
        this.type = type;
        this.amount = amount;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return tableNumber + ","+type +"," + amount;
    }
}
