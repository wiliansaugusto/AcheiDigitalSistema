<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Endereco extends Model
{
    protected $table = 'enderecos';
    protected $primaryKey = 'id_endereco';
    public $timestamps = false;

    protected $fillable = [
        'tp_endereco',
        'logradouro',
        'numero',
        'bairro',
        'complemento',
        'cidade',
        'estadoSigla',
        'CEP',
        'id_usuario',
    ];

    protected $casts = [
        'dataAlteracao' => 'datetime',
    ];
    public function usuarios()
    {
        return $this->belongsTo(Usuario::class, 'id_usuario', 'id_usuario');
    }
}
