<?php

namespace App\Http\Controllers;

use App\Models\Endereco;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;
use App\Models\Usuario;

class EnderecoController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        try {
            $this->validate($request, [
                'tp_endereco' => "required",
                'logradouro' => 'required',
                'bairro' => 'required',
                'cidade' => 'required',
                'estadoSigla' => 'required',
                'CEP' => 'required',
                'id_usuario' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }


        if (!Usuario::find($request->id_usuario)) {
            return response()->json(['message' => 'Usuario não encontrado'], 400);
        }
        if ($request->id_endereco) {
            $endereco = Endereco::find($request->id_endereco);
            if ($endereco->id_usuario != $request->id_usuario) {
                return response()->json(['message' => 'Endereço não pertence ao usuario informado'], 400);
            }
            $endereco->update($request->all());
            return response()->json($endereco, 200);
        } else {
            $contato = Endereco::create($request->all());
            return response()->json($contato, 201);
        }
    }
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($idEndereco, $idUsuario)
    {
        $endereco = Endereco::where('id_endereco', $idEndereco)->where('id_usuario', $idUsuario)->first();
        if ($endereco) {
            $endereco->delete();
            return response()->json(['message' => 'Endereço deletado com sucesso'], 202);
        } else {
            return response()->json(['message' => 'Endereço não encontrado'], 400);
        }
    }
}
