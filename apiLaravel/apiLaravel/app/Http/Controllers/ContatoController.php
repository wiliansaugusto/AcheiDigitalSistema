<?php

namespace App\Http\Controllers;

use App\Models\Contato;
use App\Models\Usuario;
use Illuminate\Http\Request;
use Illuminate\Validation\ValidationException;

class ContatoController extends Controller
{


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
                'tp_contato' => "required",
                'ds_contato' => 'required',
                'id_usuario' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }


        if (!Usuario::find($request->id_usuario)) {
            return response()->json(['message' => 'Usuario não encontrado'], 400);
        }
        if ($request->id_contato) {
            $contato = Contato::find($request->id_contato);
            if ($contato->id_usuario != $request->id_usuario) {
                return response()->json(['message' => 'Contato não pertence ao usuario informado'], 400);
            }
            $contato->update($request->all());
            return response()->json($contato, 200);
        } else {
            $contato = Contato::create($request->all());
            return response()->json($contato, 201);
        }
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function storeContatoExtra(Request $request)
    {
        try {
            $this->validate($request, [
                'tp_contato' => "required",
                'ds_contato' => 'required',
                'id_usuario' => 'required',
                'nome_extra' => 'required',
                'contato_extra' => 'required'
            ]);
        } catch (ValidationException $e) {
            return response()->json($e->errors(), 400);
        }


        if (!Usuario::find($request->id_usuario)) {
            return response()->json(['message' => 'Usuario não encontrado'], 400);
        }
        if ($request->id_contato) {
            $contato = Contato::find($request->id_contato);
            if ($contato->id_usuario != $request->id_usuario) {
                return response()->json(['message' => 'Contato não pertence ao usuario informado'], 400);
            }
            $contato->update($request->all());
            return response()->json($contato, 200);
        } else {
            $contato = Contato::create($request->all());
            return response()->json($contato, 201);
        }
    }
    public function listarContatoExtra($idcontato)
    {
        $contatos = Contato::where('contato_extra', 1)->where('id_usuario', $idcontato)->get();
        return response()->json($contatos, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($idcontato, $idUsuario)
    {
        $contato = Contato::where('id_contato', $idcontato)->where('id_usuario', $idUsuario)->first();
        if ($contato) {
            $contato->delete();
            return response()->json(['message' => 'Contato deletado com sucesso'], 200);
        } else {
            return response()->json(['message' => 'Contato não encontrado'], 400);
        }
    }
}
