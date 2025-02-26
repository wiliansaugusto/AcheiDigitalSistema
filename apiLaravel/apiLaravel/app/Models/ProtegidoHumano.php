<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class ProtegidoHumano extends Model
{
    protected $table = 'protegidos_humano';
    protected $primaryKey = 'id_protegido_humano';
    public $timestamps = false;

    protected $fillable = [
        'nome_protegido',
        'altura',
        'peso',
        'patologias',
        'uso_medicacoes',
        'observacoes_gerais',
        'imagem',
        'id_usuario',
        'dataNascimento',
        'alergias'
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
