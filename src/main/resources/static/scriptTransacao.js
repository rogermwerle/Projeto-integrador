$(document).ready(function () {

    // Função para carregar o histórico de transferências
    function carregarTransacoes() {
        $.ajax({
            url: `http://localhost:8080/transacoes/listar`,
            method: "GET",
            success: function (transacoes) {
                const tabela = $("table tbody");
                tabela.empty();

                transacoes.forEach(transacao => {
                    tabela.append(`
                        <tr>
                            <td>${transacao.id}</td>
                            <td>${new Date(transacao.dataHora).toLocaleString()}</td>
                            <td>${transacao.remetente.id}</td>
                            <td>${transacao.destinatario.id}</td>
                            <td>${transacao.tipo}</td>
                            <td>R$ ${transacao.valor.toFixed(2)}</td>
                            <td>${transacao.descricao}</td>
                            <td>
                                <button class="btn btn-danger btn-sm" onclick="excluirTransacao(${transacao.id})">Excluir</button>
                            </td>
                        </tr>
                    `);
                });
            },
            error: function () {
                alert("Erro ao carregar o histórico de transferências.");
            }
        });
    }

    $("#formTransacao").submit(function (event) {
        event.preventDefault();

        const remetenteId = $("#remetente").val();
        const destinatarioId = $("#destinatario").val();
        const valor = parseFloat($("#valor").val());
        const descricao = $("#descricao").val();

        if (valor <= 0) {
            alert("O valor deve ser maior que zero.");
            return;
        }

        $.ajax({
            url: `http://localhost:8080/transacoes/transferir`,
            method: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: {
                remetenteId: remetenteId,
                destinatarioId: destinatarioId,
                valor: valor,
                descricao: descricao
            },
            success: function () {
                alert("Transferência realizada com sucesso!");
                $("#formTransacao")[0].reset();
                carregarTransacoes();
            },
            error: function (xhr) {
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    alert(`Erro: ${xhr.responseJSON.message}`);
                } else {
                    alert("Erro ao realizar a transferência.");
                }
            }
        });
    });

    window.excluirTransacao = function (id) {
        if (confirm("Deseja realmente excluir esta transferência?")) {
            $.ajax({
                url: `http://localhost:8080/transacoes/deletar/${id}`,
                method: "DELETE",
                success: function () {
                    alert("Transferência excluída com sucesso.");
                    carregarTransacoes(); // Atualiza a tabela
                },
                error: function () {
                    alert("Erro ao excluir a transferência.");
                }
            });
        }
    };

    carregarTransacoes();
});