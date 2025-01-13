$(document).ready(function () {
    $('#btnBuscar').on('click', function () {
        const userId = $('#pegarID').val();

        if (userId) {
            $.ajax({
                url: `http://localhost:8080/usuarios/buscar/${userId}`,
                method: 'GET',
                dataType: 'json',
                success: function (response) {
                    $('#nomeUsuario').text(response.nome);
                    $('#saldoUsuario').text(`R$ ${response.saldo.toFixed(2)}`);
                    $('#formSaldoAtualizar').removeClass('d-none');
                },
                error: function (xhr) {
                    if (xhr.status === 404) {
                        alert('Usuário não encontrado. Tente novamente.');
                    } else {
                        alert('Ocorreu um erro ao buscar os dados. Tente novamente mais tarde.');
                    }
                    $('#formSaldoAtualizar').addClass('d-none');
                }
            });
        } else {
            alert('Por favor, insira um ID válido.');
        }
    });

    $('#formSaldoAtualizar').on('submit', function (event) {
        event.preventDefault(); 

        const userId = $('#pegarID').val();
        const valor = parseFloat($('#saldoAtualizar').val()); 
        const tipoOperacao = $('input[name="saldo-tipo"]:checked').val();

        if (!userId || !valor || !tipoOperacao) {
            alert('Por favor, preencha todos os campos antes de continuar.');
            return;
        }

        const endpoint = tipoOperacao === 'depositar' 
            ? `http://localhost:8080/usuarios/depositar/${userId}` 
            : `http://localhost:8080/usuarios/sacar/${userId}`;

        $.ajax({
            url: endpoint,
            method: 'POST',
            data: { valor: valor },
            success: function (response) {
                $('#saldoUsuario').text(`R$ ${response.saldo.toFixed(2)}`);
                alert(`Operação de ${tipoOperacao} realizada com sucesso!`);
            },
            error: function () {
                alert('Ocorreu um erro ao processar a informação');
            }
        });
    });
});
