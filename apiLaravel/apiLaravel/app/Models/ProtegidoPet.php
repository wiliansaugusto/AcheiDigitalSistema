<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class ProtegidoPet extends Model
{
    protected $table = 'protegido_pets';
    protected $primaryKey = 'id_protegido_pet';
    public $timestamps = false;

    protected $fillable = [
        'nome_protegido_pet',
        'peso',
        'patologias',
        'uso_medicacoes',
        'observacoes_gerais',
        'imagem',
        'raca',
        'especie',
        'id_usuario',

    ];

    protected $casts = [
        'dataAlteracao' => 'datetime',
        'dataCriacao' => 'datetime',
        'dataNascimento' => 'datetime'
    ];


    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'id_usuario', 'id_usuario');
    }
}
