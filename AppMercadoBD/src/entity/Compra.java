package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import util.TipoPagamento;

/**
 *
 * @author andre; arthur
 */
public class Compra {
    private List<ItemComprado> itemComprado;//
    private double precoTotal;
    private LocalDate dataCompra;//
    private boolean pedidoEntrega;//
    private TipoPagamento tipoPagamento;
    private Endereco enderecoEntrega;//
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Compra(List<ItemComprado> itemComprado, double precoTotal, LocalDate dataCompra, boolean pedidoEntrega, TipoPagamento tipoPagamento) {
        setItemComprado(itemComprado);
        setPrecoTotal(precoTotal);
        setDataCompra(dataCompra);
        setPedidoEntrega(pedidoEntrega);
        setTipoPagamento(tipoPagamento);
    }

    public Compra(List<ItemComprado> itemComprado, double precoTotal, LocalDate dataCompra, boolean pedidoEntrega, TipoPagamento tipoPagamento, Endereco endrecoEntrega) {
        this(itemComprado, precoTotal, dataCompra, pedidoEntrega, tipoPagamento);
        setEnderecoEntrega(enderecoEntrega);
    }

    public List<ItemComprado> getItemComprado() {
        return itemComprado;
    }

    public void setItemComprado(List<ItemComprado> itemComprado) {
        this.itemComprado = itemComprado;
    }
    
    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public String getDataCompraString() {
        return dataCompra.format(formatter);
    }

    public void setDataCompra(LocalDate dataCompra) {
        Objects.requireNonNull(dataCompra);
        this.dataCompra = dataCompra;
    }

    public boolean getPedidoEntrega() {
        return pedidoEntrega;
    }

    public void setPedidoEntrega(boolean pedidoEntrega) {
        this.pedidoEntrega = pedidoEntrega;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        
        this.enderecoEntrega = enderecoEntrega;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public String toString() {
        if (getPedidoEntrega()) {
            return "Compra do dia: " + getDataCompraString() + "\nPedido de Entrega: Sim" + "\nEndereço: " + getEnderecoEntrega() 
                    + "\n" + itemComprado.toString() + "\nTotal: " + getPrecoTotal() + "\nForma de Pagamento: " + getTipoPagamento().getTipoProduto();
        }
        return "Compra do dia: " + getDataCompraString() + "\nPedido de Entrega: Não\n" + itemComprado.toString() + "Total: " + getPrecoTotal() + "\nForma de Pagamento: " + getTipoPagamento().getTipoProduto();
    }
}
