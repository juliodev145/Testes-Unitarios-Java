package sistemabancario;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

/**
 * Classe responsável por testar as principais operações
 * realizadas pela classe {@link GerenciadoraClientes}.
 *
 * @author julio.pinto
 * @date 30/09/2026
 */
public class GerenciadoraClientesTest {

    private GerenciadoraClientes gerClientes;

    private final int idCliente01 = 1;
    private final int idCliente02 = 2;

    /**
     * Monta o cenário inicial antes de cada teste.
     *
     * O método é executado automaticamente pelo JUnit
     * antes de cada método anotado com "@Test".
     */
    @Before
    public void setUp() {

        Cliente cliente1 = new Cliente(idCliente01, "Jarbas", 45, "jarbas@email.com", 1, true);
        Cliente cliente2 = new Cliente(idCliente02, "Lucas", 18, "lucas@email.com", 2, true);

        List<Cliente> clientesDoBanco = new ArrayList<Cliente>();

        clientesDoBanco.add(cliente1);
        clientesDoBanco.add(cliente2);

        gerClientes = new GerenciadoraClientes(clientesDoBanco);
    }

    /**
     * Limpa o cenário após cada teste,
     * garantindo a independência entre os testes.
     */
    @After
    public void tearDown() {
        gerClientes.limpa();
    }

    /**
     * Verifica se é possível pesquisar um cliente existente.
     */
    @Test
    public void testPesquisaCliente() {
    	
    	//1a fase - cenario - "@Before"

    	//2a fase - execucao
        Cliente clienteRetorno = gerClientes.pesquisaCliente(idCliente01);

        //3a fase - avaliacao
        assertThat(clienteRetorno.getId(), is(idCliente01));
        assertThat(clienteRetorno.getNome(), is("Jarbas"));
    }

    /**
     * Verifica o comportamento da pesquisa
     * quando o cliente não existe.
     */
    @Test
    public void testPesquisaClienteInexistente() {

        Cliente clienteRetorno = gerClientes.pesquisaCliente(40);

        assertNull(clienteRetorno);
    }

    /**
     * Verifica se um cliente existente pode ser removido.
     */
    @Test
    public void testRemoveCliente() {

        boolean resultadoRetorno = gerClientes.removeCliente(idCliente01);

        assertThat(resultadoRetorno, is(true));
        assertThat(gerClientes.getClientesDoBanco().size(), is(1));
        assertThat(gerClientes.pesquisaCliente(idCliente02).getId(), is(idCliente02));
        assertNull(gerClientes.pesquisaCliente(idCliente01));
    }

    /**
     * Verifica o comportamento da remoção
     * quando o cliente não existe.
     */
    @Test
    public void testRemoveClienteInexistente() {

        boolean resultadoRetorno =
                gerClientes.removeCliente(40);

        assertThat(resultadoRetorno, is(false));
        assertThat(gerClientes.getClientesDoBanco().size(), is(2));
        assertNull(gerClientes.pesquisaCliente(40));
    }

    /**
     * Valida um cliente que está no centro
     * da faixa de idade permitida. 30 anos
     */
    @Test
    public void testClienteIdadePermitida1() throws IdadeNaoPermitidaException {

        Cliente cliente = new Cliente(3, "Joao", 30, "joao@email.com", 10, true);

        boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

        assertTrue(idadeValida);
    }

    /**
     * Valida o menor extremo da faixa
     * de idade permitida.  18 anos
     */
    @Test
    public void testClienteIdadePermitida2() throws IdadeNaoPermitidaException {

        Cliente cliente = new Cliente(4, "Enzo", 18, "enzo@email.com", 11, true);

        boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

        assertTrue(idadeValida);
    }

    /**
     * Valida o maior extremo da faixa
     * de idade permitida.  65 anos
     */
    @Test
    public void testClienteIdadePermitida3() throws IdadeNaoPermitidaException {

        Cliente cliente = new Cliente(5, "Joaquim", 65, "joca@email.com", 12, true);

        boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

        assertTrue(idadeValida);
    }

    /**
     * Verifica se uma idade acima do limite
     * permitido gera a exceção esperada.  66 anos
     */
    @Test
    public void testClienteIdadeAcimaDoPermitido() throws IdadeNaoPermitidaException {

        Cliente cliente = new Cliente(6, "Marcos", 66, "marcos@email.com", 21, true);

        try {

            gerClientes.validaIdade(cliente.getIdade());

            fail();

        } catch (IdadeNaoPermitidaException e) {

            assertThat(e.getMessage(),is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
        }
    }

    /**
     * Verifica se uma idade abaixo do limite
     * permitido gera a exceção esperada.  17 anos
     */
    @Test
    public void testClienteIdadeAbaixoDoPermitido() throws IdadeNaoPermitidaException {

        Cliente cliente = new Cliente(7,"Claudio",17,"claudio@email.com", 6, true);

        try {

            gerClientes.validaIdade(cliente.getIdade());

            fail();

        } catch (IdadeNaoPermitidaException e) {

            assertThat(e.getMessage(),is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
        }
    }
}