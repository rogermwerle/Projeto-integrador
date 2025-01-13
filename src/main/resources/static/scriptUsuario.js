$(document).ready(function () {

    // novo usuario
    $('#formAddUsuario').on('submit', function (event) {
        event.preventDefault();

        const usuario = {
            nome: $('#nome').val(),
            saldo: $('#saldo').val() || 0
        };

        $.ajax({
            url: 'http://localhost:8080/usuarios/cadastrar',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(usuario),
            success: function () {
                alert('Usuário cadastrado com sucesso!');
                $('#formAddUsuario')[0].reset();
            },
            error: function (xhr, status, error) {
                alert('Erro ao cadastrar usuário: ' + error);
                console.log(xhr.responseText);
            }
        });
    });

    //carregar usuarios
    function carregarUsuarios() {
        $.get('http://localhost:8080/usuarios/listar', function (usuarios) {
            const tableBody = $('#tableUsuarios tbody');
            tableBody.empty();
            usuarios.forEach(usuario => {
                const row = `
                    <tr>
                        <td>${usuario.id}</td>
                        <td>${usuario.nome}</td>
                        <td>${usuario.saldo}</td>
                        <td>
                            <button class="editar" data-id="${usuario.id}">Editar</button>
                            <button class="remover" data-id="${usuario.id}">Remover</button>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });
            $('.editar').on('click', editarUsuario);
            $('.remover').on('click', removerUsuario);
        });
    }

    // evento de editar usuário
    function editarUsuario() {
        const id = $(this).data('id');
        $.get(`http://localhost:8080/usuarios/buscar/${id}`, function (usuario) {
            $('#nome').val(usuario.nome);  
            $('#formAtualizarUsuario').data('id', id);
        });
    }

    $('#formAtualizarUsuario').on('submit', function (event) {
        event.preventDefault();

        const id = $(this).data('id');
        if (!id) {
            alert('Você deve clicar em "Editar" antes de atualizar!');
            return;
        }

        const usuarioAtualizado = {
            nome: $('#nome').val()
        };

        $.ajax({
            url: `http://localhost:8080/usuarios/alterar-nome/${id}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(usuarioAtualizado),
            success: function () {
                alert('Usuário atualizado com sucesso!');
                $('#formAtualizarUsuario')[0].reset();
                $('#formAtualizarUsuario').removeData('id');
                carregarUsuarios();
            },
            error: function (xhr, status, error) {
                alert('Erro ao atualizar usuário: ' + error);
                console.log(xhr.responseText);
            }
        });
    });

    // evento de remover usuário
    function removerUsuario() {
        const id = $(this).data('id');

        if (confirm('Tem certeza que deseja remover este Usuário?')) {
            $.ajax({
                url: `http://localhost:8080/usuarios/deletar/${id}`,
                type: 'DELETE',
                success: function () {
                    alert('Usuário removido com sucesso!');
                    carregarUsuarios();
                },
                error: function (xhr, status, error) {
                    alert('Erro ao remover usuário: ' + error);
                    console.log(xhr.responseText);
                }
            });
        }
    }

    carregarUsuarios();
});
