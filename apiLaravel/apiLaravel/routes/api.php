<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UsuarioController;
use App\Http\Controllers\ContatoController;
use App\Http\Controllers\EnderecoController;
use App\Http\Controllers\LogarController;
use App\Http\Controllers\EnviarEmailController;
use App\Http\Controllers\ProtegidoHumanoController;
use App\Http\Controllers\ProtegidoPetController;
use App\Http\Controllers\TagHumanoController;
use App\Http\Controllers\TagPetController;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/teste', function () {
    return "1 2 3 testando...";
});

Route::post('/usuario-novo', [UsuarioController::class, 'salvarUsuario']);
Route::post('/usuario-editar', [UsuarioController::class, 'editarUsuario']);
Route::get('/usuario-listar-todos', [UsuarioController::class, 'listarTodosUsuarios']);
Route::get('/usuario-editar-status/{id}', [UsuarioController::class, 'alterarStatusUsuario']);
Route::get('/usuario-listar-id/{id}', [UsuarioController::class, 'listarUsuarioID']);
Route::get('/usuario-deletar/{id}', [UsuarioController::class, 'deletarUsuario']);
Route::get('/usuario-listar-cpf/{cpf}', [UsuarioController::class, 'listarUsuarioCPF']);
Route::get('/usuario-listar-protegido/{id}', [UsuarioController::class, 'buscarUsuarioProtegido']);

Route::post('/editar-contato', [ContatoController::class, 'store']);
Route::delete('/deletar-contato/{idContato}/{idUsuario}', [ContatoController::class, 'destroy']);
Route::post('/contato-extra', [ContatoController::class, 'storeContatoExtra']);
Route::get('/listar-contato-extra/{idContato}', [ContatoController::class, 'listarContatoExtra']);

Route::post('/editar-endereco', [EnderecoController::class, 'store']);
Route::delete('/deletar-endereco/{idEndereco}/{idUsuario}', [EnderecoController::class, 'destroy']);

Route::post('/logar', [LogarController::class, 'login']);
Route::post('/esqueci-a-senha', [LogarController::class, 'esqueciSenha']);

Route::post('/enviar-email', [EnviarEmailController::class, 'enviarEmailGenerico']);

Route::post('/salvar-protegido-humano', [ProtegidoHumanoController::class, 'salvarProtegidoHumano']);
Route::put('/editar-protegido-humano', [ProtegidoHumanoController::class, 'editarProtegidoHumano']);
Route::get('/listar-protegidos-humano-por-usuario/{id}', [ProtegidoHumanoController::class, 'listarProtegidoHumanoUsuario']);
Route::get('/pesquisar-protegido-humano/{id}', [ProtegidoHumanoController::class, 'pesquisarProtegidoHumanoPorID']);
Route::delete('/deletar-protegido-humano/{id}', [ProtegidoHumanoController::class, 'deletarProtegidoHumano']);


Route::post('/pet/salvar-protegido-pet', [ProtegidoPetController::class, 'salvarProtegidoPet']);
Route::put('/pet/editar-protegido-pet', [ProtegidoPetController::class, 'editarProtegidoHumano']);
Route::get('/pet/listar-protegidos-pet-por-usuario/{id}', [ProtegidoPetController::class, 'listarProtegidoPetUsuario']);
Route::get('/pet/pesquisar-protegido-pet/{id}', [ProtegidoPetController::class, 'pesquisarProtegidoPetPorID']);
Route::delete('/pet/deletar-protegido-pet/{id}', [ProtegidoPetController::class, 'deletarProtegidoPet']);

Route::post('/gravar-tag', [TagHumanoController::class, 'gravarTag']);
Route::post('/editar-tag', [TagHumanoController::class, 'editarTag']);
Route::get('/tag/{id}', [TagHumanoController::class, 'tagID']);
Route::get('/listar-tags-id-usuario/{id}', [TagHumanoController::class, 'listarTagID']);
Route::delete('/liberar-tag/{id}', [TagHumanoController::class, 'liberarTag']);

Route::post('/pet/gravar-tag-pet', [TagPetController::class, 'gravarTagPet']);
Route::post('/pet/editar-tag-pet', [TagPetController::class, 'editarTagPet']);
Route::get('/pet/tag/{id}', [TagPetController::class, 'tagIDPet']);
Route::get('/pet/listar-tags-id-usuario/{id}', [TagPetController::class, 'listarTagIDPet']);
Route::delete('/pet/liberar-tag/{id}', [TagPetController::class, 'liberarTagPet']);
