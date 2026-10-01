package sistemabancario;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraContasTest {

    private GerenciadoraContas gerContas;

    private ContaCorrente conta01;
    private ContaCorrente conta02;

    private final int idConta01 = 1;
    private final int idConta02 = 2;

    @Before
    public void setUp() {

        conta01 = new ContaCorrente(idConta01, 200, true);
        conta02 = new ContaCorrente(idConta02, 0, true);

        List<ContaCorrente> contasDoBanco = new ArrayList<>();

        contasDoBanco.add(conta01);
        contasDoBanco.add(conta02);

        gerContas = new GerenciadoraContas(contasDoBanco);
    }

    @After
    public void tearDown() {

        gerContas.limpa();
    }

    @Test
    public void testTransfereValor() {

        boolean sucesso = gerContas.transfereValor(idConta01, 50, idConta02);

        assertThat(sucesso, is(true));
        assertThat(conta01.getSaldo(), is(150.0));
        assertThat(conta02.getSaldo(), is(50.0));
    }

    @Test
    public void testTransfereValorAcimaDoSaldo() {

        conta01.setSaldo(100);

        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        assertThat(sucesso, is(true));
        assertThat(conta01.getSaldo(), is(-100.0));
        assertThat(conta02.getSaldo(), is(200.0));
    }

    @Test
    public void testTransfereValorComSaldoNegativo() {

        conta01.setSaldo(-100);

        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        assertThat(sucesso, is(true));
        assertThat(conta01.getSaldo(), is(-300.0));
        assertThat(conta02.getSaldo(), is(200.0));
    }

    @Test
    public void testTransfereValorComContasComSaldoNegativo() {

        conta01.setSaldo(-100);
        conta02.setSaldo(-100);

        boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);

        assertThat(sucesso, is(true));
        assertThat(conta01.getSaldo(), is(-300.0));
        assertThat(conta02.getSaldo(), is(100.0));
    }
}